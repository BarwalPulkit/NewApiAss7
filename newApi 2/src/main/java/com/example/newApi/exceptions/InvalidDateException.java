package com.example.newApi.exceptions;

public class InvalidDateException extends IllegalArgumentException{
    public InvalidDateException(String message){
        super(message);
    }
}
