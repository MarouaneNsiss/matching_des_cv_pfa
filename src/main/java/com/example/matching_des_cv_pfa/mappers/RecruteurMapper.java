package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Recruteur;

public interface RecruteurMapper {
    Recruteur fromRecruteurDTO(RecruteurDTO recruteurDTO);
    RecruteurDTO fromRecruteur(Recruteur recruteur);
}
