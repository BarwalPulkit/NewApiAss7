package com.example.newApi.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardCodedData {

    private List<String> countries;
    private List<String> categories;

    private String[] countriesArray = {"us", "banana", "orange"};
    private String[] categoriesArray = {"business", "banana", "orange"};


    public void loadData(){
        this.countries = Arrays.asList(countriesArray);
        this.categories = Arrays.asList(categoriesArray);
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
