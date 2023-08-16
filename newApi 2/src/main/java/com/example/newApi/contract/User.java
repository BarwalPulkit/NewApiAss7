package com.example.newApi.contract;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String userId;
    private String email;
    private String country;
    private String category;

    private List<String> choosenResources;

    public User(String email, String country, String category, List<String> choosenResources) {
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this.country = country;
        this.category = category;
        this.choosenResources = choosenResources;
    }

    public User(String userId, String email, String country, String category, List<String> choosenResources) {
        this.userId = userId;
        this.email = email;
        this.country = country;
        this.category = category;
        this.choosenResources = choosenResources;
    }

    public List<String> getChoosenResources() {
        return choosenResources;
    }

    public void setChoosenResources(List<String> choosenResources) {
        this.choosenResources = choosenResources;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
