package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.BeneficiareDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;

public interface BeneficiaireMapper {
    BeneficiareDTO fromBeneficaire(Beneficiaire beneficiaire);

    Beneficiaire fromBeneficaireDTO(BeneficiareDTO beneficiareDTO);
}
