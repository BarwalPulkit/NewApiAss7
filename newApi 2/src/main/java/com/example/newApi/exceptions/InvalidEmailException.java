package com.example.newApi.exceptions;

public class InvalidEmailException extends IllegalArgumentException{
    public InvalidEmailException(String message){
        super(message);
    }
}
