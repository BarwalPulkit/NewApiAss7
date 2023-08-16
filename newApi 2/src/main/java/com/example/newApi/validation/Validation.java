package com.example.newApi.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final Set<String> VALID_COUNTRIES = new HashSet<>(Arrays.asList(
            "ae", "ar", "au", "at", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz",
            "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp",
            "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt",
            "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us",
            "ve", "za", "bt"
    ));

    private static final Set<String> VALID_CATEGORIES = new HashSet<>(Arrays.asList("entertainment", "general", "health", "science", "sports", "technology", "business"));

    public static boolean emailCheck(String email){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean countryCheck(String country){
        return VALID_COUNTRIES.contains(country);
    }

    public static boolean categoryCheck(String category){
        return VALID_CATEGORIES.contains(category);
    }

    public static boolean checkPublishDate(String publishedAt, String from, String to){

        String[] publishedAtSplit = publishedAt.split("T");
        LocalDate publishDate = LocalDate.parse(publishedAtSplit[0]);

        if(from.equals(""))
            from = "2000-01-01";

        if(to.equals(""))
            to = LocalDate.now().toString();

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        LocalDate ago_30 = LocalDate.now().minus(Period.ofDays(30));

        if(publishDate.isAfter(ago_30) && fromDate.isBefore(toDate) && publishDate.isBefore(toDate) && publishDate.isAfter(fromDate))
            return true;

        return false;


    }
}
