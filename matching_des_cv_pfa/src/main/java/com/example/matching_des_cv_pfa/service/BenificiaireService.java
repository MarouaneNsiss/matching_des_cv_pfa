package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Beneficiaire;

import java.util.List;
import java.util.Optional;

public interface BenificiaireService {
    Beneficiaire saveBeneficiaire(Beneficiaire beneficiaire);
     List<Beneficiaire> getAllBeneficiaires();
     Optional<Beneficiaire> getBeneficiaireByEmail(String email);
}
