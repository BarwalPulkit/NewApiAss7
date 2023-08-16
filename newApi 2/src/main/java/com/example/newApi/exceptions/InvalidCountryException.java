package com.example.newApi.exceptions;

public class InvalidCountryException extends IllegalArgumentException{
    public InvalidCountryException(String message){
        super(message);
    }
}
