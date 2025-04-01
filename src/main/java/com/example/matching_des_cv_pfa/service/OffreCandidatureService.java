package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.CandidatDTO;
import com.example.matching_des_cv_pfa.dto.OffreCandidatureDTO;
import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OffreCandidatureService {
    OffreCandidatureDTO applyTOffre(Long beneficiareId, Long OffreId) throws BeneficiaireNotFoundException, OffreNotFoundException;
    OffreCandidatureDTO updateCandidatureStatus(Long candidatureId, CandidatureStatus newStatus);
    Page<OffreCandidatureDTO> getCandidaturesByBeneficiaire(Long beneficiaireId, int page, int size) throws BeneficiaireNotFoundException;

    public Page<CandidatDTO> getCandidatsByOffre(Long recruteurId, int page, int size) throws OffreNotFoundException;

    void deleteCandidature(Long candidatureId);
}
