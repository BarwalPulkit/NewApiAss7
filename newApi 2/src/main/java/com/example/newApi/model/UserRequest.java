package com.example.newApi.model;

import java.util.List;

public class UserRequest {
    private String country;
    private String category;

    public UserRequest(String country, String category) {
        this.country = country;
        this.category = category;
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
