package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BeneficiaireService {
    BeneficiaireDTO saveBeneficiaire(Beneficiaire beneficiaire);
     BeneficiaireDTO getBeneficiaireByEmail(String email) throws BeneficiaireNotFoundException;

    BeneficiaireDTO createBeneficiaire(
            BeneficiaireDTO beneficiaireDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException;

    BeneficiaireDTO updateBeneficiaire(
            Long id,
            BeneficiaireDTO beneficiaireDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException, BeneficiaireNotFoundException;

    BeneficiaireDTO getBeneficiare(Long beneficiareId) throws BeneficiaireNotFoundException;
    Page<BeneficiaireDTO> getAllBeneficiaires(int page, int size);
}
