package com.example.newApi.service;

import com.example.newApi.model.EmailDetails;

public interface EmailService {
    public String sendSimpleMail(EmailDetails emailDetails);
}
