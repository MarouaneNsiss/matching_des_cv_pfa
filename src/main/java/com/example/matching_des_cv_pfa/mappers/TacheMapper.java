package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.TacheDTO;
import com.example.matching_des_cv_pfa.entities.Tache;

public interface TacheMapper {
    TacheDTO fromTache(Tache tache);

    Tache fromTachDTO(TacheDTO tacheDTO);
}
