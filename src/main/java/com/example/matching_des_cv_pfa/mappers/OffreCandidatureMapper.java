package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.CandidatDTO;
import com.example.matching_des_cv_pfa.dto.OffreCandidatureDTO;
import com.example.matching_des_cv_pfa.entities.OffreCandidature;

public interface OffreCandidatureMapper {
    OffreCandidatureDTO fromOffreCandidature(OffreCandidature offreCandidature);
    CandidatDTO toCandidatDTO(OffreCandidature offreCandidature);

}
