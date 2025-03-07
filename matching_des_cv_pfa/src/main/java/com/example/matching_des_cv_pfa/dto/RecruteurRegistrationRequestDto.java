package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.Gender;

public record RecruteurRegistrationRequestDto(String entreprise, String adresse, String ville, String pays, int number_employees,String site,
                                         byte[] logo  ,Gender gender,String firstName, String lastName,String jobFunction ,String telephone , String email, String password) {
}
