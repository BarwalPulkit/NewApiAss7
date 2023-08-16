package com.example.newApi.exceptions;

public class InvalidCategoryException extends IllegalArgumentException{
    public InvalidCategoryException(String message){
        super(message);
    }
}
