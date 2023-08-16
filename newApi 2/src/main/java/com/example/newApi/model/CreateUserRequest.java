package com.example.newApi.model;

import java.util.List;

public class CreateUserRequest {

    private String email;
    private String country;
    private String category;
    private List<String> preferedSources;

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

    public List<String> getpreferedSources() {
        return preferedSources;
    }

    public void setpreferedSources(List<String> preferedSources) {
        this.preferedSources = preferedSources;
    }
}
