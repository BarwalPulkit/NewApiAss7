package com.example.newApi.exceptions;

public class UserAlreadyExistException extends IllegalArgumentException{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
