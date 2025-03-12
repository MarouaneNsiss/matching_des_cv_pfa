package com.example.matching_des_cv_pfa.service;

public interface MailService {
    void sendEmail(String emailDestination, String subject, String content);
}
