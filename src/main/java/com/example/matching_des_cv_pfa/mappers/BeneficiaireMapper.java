package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;

public interface BeneficiaireMapper {
    BeneficiaireDTO fromBeneficaire(Beneficiaire beneficiaire);

    Beneficiaire fromBeneficaireDTO(BeneficiaireDTO beneficiaireDTO);

    BeneficiaireDTO ToBeneDTOwithoutExperiences(Beneficiaire beneficiaire);
}
