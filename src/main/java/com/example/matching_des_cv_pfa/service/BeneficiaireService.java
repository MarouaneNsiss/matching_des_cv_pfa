package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BeneficiareDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BeneficiaireService {
    BeneficiareDTO saveBeneficiaire(Beneficiaire beneficiaire);
     List<Beneficiaire> getAllBeneficiaires();
     BeneficiareDTO getBeneficiaireByEmail(String email) throws BeneficiaireNotFoundException;

    BeneficiareDTO createBeneficiaire(
            BeneficiareDTO beneficiareDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException;

    BeneficiareDTO updateBeneficiaire(
            Long id,
            BeneficiareDTO beneficiareDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException, BeneficiaireNotFoundException;

    BeneficiareDTO getBeneficiare(Long beneficiareId) throws BeneficiaireNotFoundException;
}
