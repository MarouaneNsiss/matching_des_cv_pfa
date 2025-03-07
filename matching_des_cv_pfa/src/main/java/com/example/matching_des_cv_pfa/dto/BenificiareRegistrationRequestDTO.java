package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.Gender;

public record BenificiareRegistrationRequestDTO(String firstName, String lastName, String email, String password,
                                                Gender gender) {
}
