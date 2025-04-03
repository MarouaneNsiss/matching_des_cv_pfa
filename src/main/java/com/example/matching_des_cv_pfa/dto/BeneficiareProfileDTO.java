package com.example.matching_des_cv_pfa.dto;


import com.example.matching_des_cv_pfa.enums.Gender;

import java.util.List;

//this DTO is for displaying just the nessecary information about beneficiaire profile
public class BeneficiareProfileDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String bio;
    private String email;
    private String telephone;
    private String adresse;

    private String imagePath;
    private Gender gender;
    private List<String> competences;

}
