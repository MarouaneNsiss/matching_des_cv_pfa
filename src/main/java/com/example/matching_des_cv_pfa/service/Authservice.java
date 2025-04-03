package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.dto.BenificiareRegistrationRequestDTO;
import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.dto.RecruteurRegistrationRequestDto;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Utilisateur;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
public interface Authservice {
    BeneficiaireDTO registerBenificiare(BenificiareRegistrationRequestDTO requestDTO, boolean activate);
    RecruteurDTO registerRecruteur(RecruteurDTO recruteurDTO, MultipartFile imageFile, boolean activate);

    Map<String,String> generateToken(String email, boolean generateRefreshToken);
    Utilisateur findUserByEmail(String email);
    void verifyEmail(Long userId);

    String emailActivation(String token);
    void passwordInitialisation(String email);

}
