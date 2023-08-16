package com.example.newApi.controller;

import com.example.newApi.contract.User;
import com.example.newApi.exceptions.*;
import com.example.newApi.model.CreateUserRequest;
import com.example.newApi.model.UserRequest;
import com.example.newApi.service.NewsApiService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.prometheus.client.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class NewsApiController {
        @Autowired
        private NewsApiService newsApiService;

        @Autowired
        private final MeterRegistry meterRegistry;
        @Autowired
        public NewsApiController(MeterRegistry meterRegistry) {
                this.meterRegistry = meterRegistry;
        }

        @GetMapping("/ping")
        public ResponseEntity<?> ping(){

                log.info("Get ping request");
                return ResponseEntity.ok("pong");
        }

        @GetMapping("/hardCodedData")
        public ResponseEntity<?> readHardCodedData(){
                log.info("GET request received for /hardCodedData");
                return ResponseEntity.ok(newsApiService.getHardCodedData());
        }

        @GetMapping("/csvCodedData")
        public ResponseEntity<?> readCsvCodedData(){
                log.info("GET request received for /csvCodedData");
                return  ResponseEntity.ok(newsApiService.getCsvCodedData());
        }

        @PostMapping("/csvEdit")
        public ResponseEntity<String> addCountriesInCsvData(@RequestBody List<UserRequest> userRequest){
                log.info("POST request received for /csvEdit");
                return ResponseEntity.ok(newsApiService.addCountriesAndCategories(userRequest));
        }

        @PostMapping("/user")
        public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
                log.info("POST request received for /user");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/user")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();

                try{

                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.createUser(createUserRequest));
                        sample.stop(timer);
                        return responseEntity;
                } catch(InvalidEmailException | InvalidCategoryException | InvalidCountryException |
                        InvalidCountryAndCategoryException | UserAlreadyExistException e){
                        log.warn("Exception occurred while creating user: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }

        @GetMapping("/headlines")
        public ResponseEntity<?> topHeadlines(@RequestParam String userId,
                                              @RequestParam(required = false, defaultValue = "99") int maxArticles,
                                              @RequestParam(required = false, defaultValue = "") String from,
                                              @RequestParam(required = false, defaultValue = "") String to,
                                              @RequestParam(required = false, defaultValue = "true") boolean matchToSources){
                log.info("GET request received for /headlines");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/headlines")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();

                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.getTopHeadlines(userId, maxArticles, from, to, matchToSources));
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(UserNotExistException | InvalidDateException e){
                        log.warn("Exception occurred while getting top headlines: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }

        @GetMapping("/sources")
        public ResponseEntity<?> getSources(@RequestParam String userId){
                log.info("GET request received for /sources");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/sources")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();
                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.getSources(userId));
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred while getting sources: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }

        @PostMapping("email-top-headline")
        public ResponseEntity<?> emailTopHeadlines(@RequestParam String userId){
                log.info("POST request received for /email-top-headline");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/email-top-headline")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();
                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.emailTopHeadlines(userId));
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred while emailing top headlines: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }
        @PostMapping("leaderboard")
        public ResponseEntity<?> getLeaderboard(){
                log.info("POST request received for /leaderboard");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/leaderboard")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();
                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.getLeaderboard());
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred while getting leaderboard: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }
        @PostMapping("metrics")
        public ResponseEntity<?> getMetrics(){
                log.info("POST request received for /metrics");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/metrics")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();

                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.getMetrics());
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred while getting metrics: {}", e.getMessage());

                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }
        @PostMapping("unsubscribe")
        public ResponseEntity<?> unsubscribe(@RequestParam String userId){
                log.info("POST request received for /unsubscribe");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/unsubscribe")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();
                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.unsubscribe(userId));
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }
        @PostMapping("subscribe")
        public ResponseEntity<?> subscribe(@RequestParam String userId){
                log.info("POST request received for /subscribe");
                Timer timer = Timer.builder("request_latency")
                        .tag("endpoint", "/subscribe")
                        .register(meterRegistry);
                Timer.Sample sample = Timer.start();
                try{
                        ResponseEntity<?> responseEntity = ResponseEntity.ok(newsApiService.subscribe(userId));
                        sample.stop(timer);
                        return responseEntity;
                }
                catch(Exception e){
                        log.warn("Exception occurred: {}", e.getMessage());
                        ResponseEntity<?> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                        sample.stop(timer);
                        return responseEntity;
                }
        }

}
