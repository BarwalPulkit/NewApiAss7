package com.example.newApi.exceptions;

public class InvalidCountryAndCategoryException extends IllegalArgumentException{

    public InvalidCountryAndCategoryException(String message){
        super(message);
    }
}
