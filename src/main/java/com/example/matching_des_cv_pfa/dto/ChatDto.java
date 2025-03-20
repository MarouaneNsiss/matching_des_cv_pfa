package com.example.matching_des_cv_pfa.dto;

public record ChatDto(  Long id;
         Date dateCreation;
         BeneficiaireDTO beneficiaire;
         RecruteurDTO recruteur;
         List<MessageDTO> messages) {
}
