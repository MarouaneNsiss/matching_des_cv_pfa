package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BenificiareRegistrationRequestDTO;
import com.example.matching_des_cv_pfa.dto.RecruteurRegistrationRequestDto;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.Map;
public interface Authservice {
    Beneficiaire registerBenificiare(BenificiareRegistrationRequestDTO requestDTO, boolean activate);
    Recruteur registerRecruteur(RecruteurRegistrationRequestDto requestDTO, boolean activate);

    Map<String,String> generateToken(String email, boolean generateRefreshToken);
    Utilisateur findUserByEmail(String email);
    void verifyEmail(Long userId);

    String emailActivation(String token);
    void passwordInitialisation(String email);

}
