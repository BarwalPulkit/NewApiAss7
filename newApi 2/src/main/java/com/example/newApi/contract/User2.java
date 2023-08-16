package com.example.newApi.contract;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "usernews")
public class User2 {
    @Id
    private String userId;
    private String email;
    private String country;
    private String category;
    private String choosenSources;
    boolean isSubscribed;


    public User2(String userId, String email, String country, String category, String choosenSources, boolean isSubscribed) {
        this.userId = userId;
        this.email = email;
        this.country = country;
        this.category = category;
        this.choosenSources = choosenSources;
        this.isSubscribed = isSubscribed;
    }

    public User2() {
    }
    public boolean getIsSubscribed() {
        return isSubscribed;
    }
    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
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

    public String getChoosenSources() {
        return choosenSources;
    }

    public void setChoosenSources(String choosenSources) {
        this.choosenSources = choosenSources;
    }
}
