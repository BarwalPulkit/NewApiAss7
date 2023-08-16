package com.example.newApi.service;

import com.example.newApi.contract.User;
import com.example.newApi.model.CreateUserRequest;
import com.example.newApi.model.MetricsRsponseDTO;
import com.example.newApi.model.UserRequest;
import com.example.newApi.reader.CsvCodedData;
import com.example.newApi.reader.HardCodedData;
import com.example.newApi.response.Article;
import com.example.newApi.response.Source;

import java.util.ArrayList;
import java.util.List;

public interface NewsApiService {

    public HardCodedData getHardCodedData();

    public CsvCodedData getCsvCodedData();

    public String addCountriesAndCategories(List<UserRequest> userRequest);

    public User createUser(CreateUserRequest createUserRequest);

    public List<Article> getTopHeadlines(String userId, int maxArticles, String from, String to, boolean matchToSources);

    public List<Source> getSources(String userId);

    public String emailTopHeadlines(String userId);
    public List<Object> getLeaderboard();
    public String unsubscribe(String userId);
    public String subscribe(String userId);
    public MetricsRsponseDTO getMetrics();

}
