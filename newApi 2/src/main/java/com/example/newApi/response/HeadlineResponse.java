package com.example.newApi.response;

import java.util.ArrayList;
import java.util.List;

public class HeadlineResponse {
    private String status;
    private String totalResults;
    private List<Article> articles;

    public HeadlineResponse(String status, String totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public HeadlineResponse() {
    }

    @Override
    public String toString(){
        StringBuilder sr = new StringBuilder();
        sr.append(status);
        sr.append(totalResults);

        for(Article article : articles)
            sr.append(article);

        return sr.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
