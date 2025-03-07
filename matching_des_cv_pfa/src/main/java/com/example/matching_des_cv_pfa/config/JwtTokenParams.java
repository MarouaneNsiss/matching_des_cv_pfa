package com.example.matching_des_cv_pfa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token")
public record JwtTokenParams (
        long shirtAccessTokenTimeout,
        long longAccessTokenTimeout,
        long refreshTokenTimeout
){}
