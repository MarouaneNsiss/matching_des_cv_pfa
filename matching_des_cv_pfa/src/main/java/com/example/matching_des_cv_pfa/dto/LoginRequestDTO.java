package com.example.matching_des_cv_pfa.dto;

public record LoginRequestDTO( String grantType, String email, String password, boolean withRefreshToken, String refreshToken) {
}
