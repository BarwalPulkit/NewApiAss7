package com.example.newApi.exceptions;

public class UserNotExistException extends IllegalArgumentException{
    public UserNotExistException(String message){
        super(message);
    }
}

