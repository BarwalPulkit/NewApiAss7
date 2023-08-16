package com.example.newApi.service;

import com.example.newApi.contract.ApiLogs;
import com.example.newApi.contract.User;
import com.example.newApi.contract.User2;
import com.example.newApi.exceptions.*;
import com.example.newApi.model.CreateUserRequest;
import com.example.newApi.model.EmailDetails;
import com.example.newApi.model.MetricsRsponseDTO;
import com.example.newApi.model.UserRequest;
import com.example.newApi.reader.CsvCodedData;
import com.example.newApi.reader.HardCodedData;
import com.example.newApi.repository.ApiCallRepository;
import com.example.newApi.repository.UserRepository;
import com.example.newApi.response.Article;
import com.example.newApi.response.HeadlineResponse;
import com.example.newApi.response.Source;
import com.example.newApi.response.SourceResponse;
import com.example.newApi.validation.Validation;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.desktop.SystemEventListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static java.lang.Math.floor;
import static java.lang.Math.min;

@Service
public class NewsApiServiceImpl implements NewsApiService{

    private HashMap<String, User> users = new HashMap<>();
    private final String API_KEY = "968045807c804799bcb183abb1e60d66";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiCallRepository apiCallRepository;

    @Autowired
    private EmailService emailService;

    public final MeterRegistry meterRegistry;

    @Autowired
    public NewsApiServiceImpl(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }

    public HardCodedData getHardCodedData(){
        HardCodedData hardCodedData = new HardCodedData();
        hardCodedData.loadData();
        return hardCodedData;
    }

    @Override
    public CsvCodedData getCsvCodedData() {
        CsvCodedData csvCodedData = new CsvCodedData();
        csvCodedData.loadData();
        return csvCodedData;
    }

    @Override
    public String addCountriesAndCategories(List<UserRequest> userRequest) {
        CsvCodedData csvCodedData = new CsvCodedData();
        try {
            csvCodedData.addData(userRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Countries are added Successfully";

    }

    @Override
    public User createUser(CreateUserRequest createUserRequest)
    {
        if(isAlreadyExist(createUserRequest.getEmail()))
            throw new UserAlreadyExistException("User already exist");

        if(!Validation.emailCheck(createUserRequest.getEmail()))
            throw new InvalidEmailException("Invalid email.");

        validityCheck(createUserRequest.getCountry(), createUserRequest.getCategory());

        User user = new User(createUserRequest.getEmail(),createUserRequest.getCountry(),createUserRequest.getCategory(),null);

        users.put(user.getUserId(), user);

        List<Source> sources = getSources(user.getUserId());
        List<String> matchingSources = new ArrayList<>();

        for(String preferedSource : createUserRequest.getpreferedSources()){
            for(Source source : sources){
                if(source.getName().equals(preferedSource)){
                    matchingSources.add(preferedSource);
                    break;
                }
            }
        }

        user.setChoosenResources(matchingSources);

        String finalChoosenSoucres = "";
        boolean start = true;
        for(String choosenResoucre : user.getChoosenResources()){
            if(start) {
                finalChoosenSoucres = choosenResoucre;
                start = false;
            }
            else
                finalChoosenSoucres = finalChoosenSoucres + "," + choosenResoucre;

        }
        if(finalChoosenSoucres.isEmpty())
            finalChoosenSoucres = null;

        User2 user2 = new User2(user.getUserId(), user.getEmail(), user.getCountry(), user.getCategory(), finalChoosenSoucres,true);

        userRepository.save(user2);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setMsgBody("Welcome......");
        emailDetails.setSubject("Simple Welcoming Mail");

        String status = emailService.sendSimpleMail(emailDetails);


        return user;
    }

    @Override
    public List<Article> getTopHeadlines(String userId, int maxArticles, String from, String to, boolean matchToSources) {

        isUserExist(userId);

        if(maxArticles<0)
            throw new IllegalArgumentException("Max-Articles should have value greater than 0.");


        User2 user2 = userRepository.findByUserId(userId);
        String[] preferedSources = null;
        List<String> choosenSources = new ArrayList<>();

        if(user2.getChoosenSources() != null) {
            preferedSources = user2.getChoosenSources().split(",");
            for (String tempSource : preferedSources) {
                choosenSources.add(tempSource);
            }
        }

        User user = new User(user2.getUserId(), user2.getEmail(), user2.getCountry(), user2.getCategory(), choosenSources);

        String resourceUrl = "https://newsapi.org/v2/top-headlines?country=" + user.getCountry() + "&category=" + user.getCategory() + "&apiKey=" + API_KEY;

        Long startTime = System.nanoTime();

        List<Article> articles = getArticles(resourceUrl);

        Long endTime = System.nanoTime();
        Long timeTaken = endTime - startTime;

        String endpoint = "https://newsapi.org/v2/top-headlines";
        String request = user.getCountry() + " and " + user.getCategory();
        String response = "";
        if(articles != null){
            for(Article article : articles){
                response = response + article.toString();
            }
        }

        ApiLogs apiLogs = new ApiLogs(endpoint, request, response, timeTaken, user2.getUserId(), LocalDate.now());
        apiCallRepository.save(apiLogs);

        List<Article> articleList = new ArrayList<>();
        List<String> choosenResources = user.getChoosenResources();

            for(Article article : articles){
                if(article.getSource()!=null) {
                    if(Validation.checkPublishDate(article.getPublishedAt(), from, to)) {
                        if(!choosenSources.isEmpty() && matchToSources) {
                            for (String sourceId : choosenResources) {
                                String articleSourceName = article.getSource().getName();
                                if (articleSourceName != null && articleSourceName.equals(sourceId)) {
                                    articleList.add(article);
                                    continue;
                                }
                            }
                        }
                        else{
                            articleList.add(article);
                        }
                    }
                }

            }

            recordHeadlinesCount(user2.getCategory(), user2.getCountry());

        maxArticles = min(maxArticles, articleList.size());

        articleList = articleList.subList(0, maxArticles);

        return articleList;

    }

    @Override
    public List<Source> getSources(String userId){

        if(!users.containsKey(userId))
            throw new UserNotExistException("User is not present.");

        User user = users.get(userId);

        String resourceUrl = "https://newsapi.org/v2/top-headlines/sources?country=" + user.getCountry() + "&category=" + user.getCategory() + "&apiKey=" + API_KEY;

        Long startTime = System.nanoTime();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SourceResponse> sourceResponseEntity =  restTemplate.getForEntity(resourceUrl, SourceResponse.class);
        SourceResponse sourceResponse = sourceResponseEntity.getBody();

        Long endTime = System.nanoTime();
        Long timeTaken = endTime - startTime;

        String endpoint = "https://newsapi.org/v2/top-headlines/sources";
        String request = user.getCountry() + " and " + user.getCategory();
        String response = sourceResponse != null ? sourceResponseEntity.toString() : null;

        ApiLogs apiLogs = new ApiLogs(endpoint, request, response, timeTaken,user.getUserId(), LocalDate.now());
        apiCallRepository.save(apiLogs);

        List<Source> sources = sourceResponse.getSources();

        return sources;

    }

    public void validityCheck(String country, String category){

        if(!Validation.countryCheck(country)){
            if(!Validation.categoryCheck(category))
                throw new InvalidCountryAndCategoryException("Both country and category is invalid.");
            else
                throw new InvalidCountryException("Country is invalid");
        }
        else{
            if(!Validation.categoryCheck(category))
                throw new InvalidCategoryException("Category is invalid");
        }

    }

    public String emailTopHeadlines(String userId){
            if(!isUserExist(userId))
                throw new UserNotExistException("User not exist");

            User2 user2 = userRepository.findByUserId(userId);
            if(!user2.getIsSubscribed()){
                return "User is not subscribed";
            }
            String category = user2.getCategory();
            String country = user2.getCountry();
            ApiLogs apiLogs = apiCallRepository.findByRequestAndDateAndEndPoint(country + " and " + category, LocalDate.now(), "https://newsapi.org/v2/top-headlines");
        //ApiLogs apiLogs = apiCallRepository.findByRequestAndDate(country + " and " + category);
           // List<Article> articles = null;
            if(apiLogs != null){
                System.out.println("From DB");
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(user2.getEmail());
                emailDetails.setSubject("Top Headlines");
                String body = "Top Headlines are : \n\n";
                body = body + apiLogs.getResponse();
                return "Top Headlines are sent Successfully";

            }
            List<Article> articles = getTopHeadlines(user2.getUserId(), 99, "", "", false);



            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user2.getEmail());
            emailDetails.setSubject("Top Headlines");
            String body = "Top Headlines are : \n\n";
            for(Article article : articles){
                body = body + article.toString() + "\n\n";
            }
            emailDetails.setMsgBody(body);

            String status = emailService.sendSimpleMail(emailDetails);


            return "Top Headlines are sent Successfully";

    }

    public List<Object> getLeaderboard(){
        List<Object> leaderboard = apiCallRepository.getLeaderboard();
        return leaderboard;
    }

    public String unsubscribe(String userId){
        if(!isUserExist(userId))
            throw new UserNotExistException("User not exist");

        User2 user2 = userRepository.findByUserId(userId);
        if(user2.getIsSubscribed()==false){
            return "User is already unsubscribed";
        }
        user2.setIsSubscribed(false);
        userRepository.save(user2);

        return "Unsubscribed Successfully";
    }
    public String subscribe(String userId){
        if(!isUserExist(userId))
            throw new UserNotExistException("User not exist");

        User2 user2 = userRepository.findByUserId(userId);
        if(user2.getIsSubscribed()==true){
            return "User is already subscribed";
        }
        user2.setIsSubscribed(true);
        userRepository.save(user2);

        return "Subscribed Successfully";
    }
    public List<Article> getArticles(String url){
        RestTemplate restTemplate = new RestTemplate();

        HeadlineResponse headlineResponse =  restTemplate.getForEntity(url, HeadlineResponse.class).getBody();

        return headlineResponse != null ? headlineResponse.getArticles() : null;

    }
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendScheduledEmail(){
        List<User2> users = userRepository.findAll();
        for(User2 user2 : users){
            if(user2.getIsSubscribed()){
                ApiLogs apiLogs = apiCallRepository.findByRequestAndDateAndEndPoint(user2.getCountry() + " and " + user2.getCategory(), LocalDate.now(), "https://newsapi.org/v2/top-headlines");
                if(apiLogs != null){
                    System.out.println("From DB");
                    EmailDetails emailDetails = new EmailDetails();
                    emailDetails.setRecipient(user2.getEmail());
                    emailDetails.setSubject("Top Headlines");
                    String body = "Top Headlines are : \n\n";
                    body = body + apiLogs.getResponse();
                    emailDetails.setMsgBody(body);
                    String status = emailService.sendSimpleMail(emailDetails);
                    continue;
                }
                List<Article> articles = getTopHeadlines(user2.getUserId(), 99, "", "", false);

                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(user2.getEmail());
                emailDetails.setSubject("Top Headlines");
                String body = "Top Headlines are : \n\n";
                for(Article article : articles){
                    body = body + article.toString() + "\n\n";
                }
                emailDetails.setMsgBody(body);

                String status = emailService.sendSimpleMail(emailDetails);
            }
        }
    }
    public MetricsRsponseDTO getMetrics(){
        List<Object[]> metricsList= apiCallRepository.getMetrics();
        Object[] metrics = metricsList.get(0);
        MetricsRsponseDTO metricsRsponseDTO = new MetricsRsponseDTO();
        metricsRsponseDTO.setNumOfCalls( metrics[0].toString());
        metricsRsponseDTO.setAvgTimeTaken(metrics[1].toString());
        metricsRsponseDTO.setP99TimeTaken(metrics[2].toString());
        return metricsRsponseDTO;
    }
    public boolean isUserExist(String userId){

        User2 user2 = userRepository.findByUserId(userId);

        if(user2 == null)
            throw new UserNotExistException("User is not present.");

        return true;
    }
    public void recordHeadlinesCount(String category, String country) {
        Counter.builder("newsapi_headlines_count")
                .description("Number of calls to get headlines for a pair of category and country")
                .tags("endpoint", "/headlines", "category", category, "country", country)
                .register(meterRegistry)
                .increment();
    }

    public boolean isAlreadyExist(String email){
        User2 user2 = userRepository.findByEmail(email);

        if(user2 != null){
            return true;
        }

        return false;
    }
}
