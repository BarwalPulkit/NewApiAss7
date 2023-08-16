package com.example.newApi.exceptions;

public class EmailSendException extends IllegalArgumentException{
    public EmailSendException(String message){
        super(message);
    }
}
