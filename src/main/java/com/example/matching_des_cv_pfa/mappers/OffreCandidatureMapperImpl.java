package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.CandidatDTO;
import com.example.matching_des_cv_pfa.dto.OffreCandidatureDTO;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.OffreCandidature;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
public class OffreCandidatureMapperImpl implements OffreCandidatureMapper {
    @Override
    public OffreCandidatureDTO fromOffreCandidature(OffreCandidature offreCandidature) {
        OffreCandidatureDTO offreCandidatureDTO = new OffreCandidatureDTO();
        offreCandidatureDTO.setId(offreCandidature.getId());
        offreCandidatureDTO.setBeneficiaireId(offreCandidature.getBeneficiaire().getId());
        offreCandidatureDTO.setCandidatureStatus(offreCandidature.getCondidatureStatus());
        offreCandidatureDTO.setOffreId(offreCandidature.getOffre().getId());
        offreCandidatureDTO.setTitle(offreCandidature.getOffre().getTitle());
        offreCandidatureDTO.setEntreprise(offreCandidature.getOffre().getRecruteur().getEntreprise());
        if (offreCandidature.getDateCandidature() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            offreCandidatureDTO.setDateCandidature(sdf.format(offreCandidature.getDateCandidature()));
        }
        if (offreCandidature.getDateUpdated()!= null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            offreCandidatureDTO.setDateUpdated(sdf.format(offreCandidature.getDateUpdated()));
        }

        return offreCandidatureDTO;
    }

    @Override
    public CandidatDTO toCandidatDTO(OffreCandidature offreCandidature) {
        CandidatDTO candidatDTO = new CandidatDTO();
        candidatDTO.setBeneficiaireId(offreCandidature.getBeneficiaire().getId());
        candidatDTO.setNom(offreCandidature.getBeneficiaire().getNom());
        candidatDTO.setPrenom(offreCandidature.getBeneficiaire().getPrenom());
        candidatDTO.setEmail(offreCandidature.getBeneficiaire().getEmail());
        candidatDTO.setGender(offreCandidature.getBeneficiaire().getGender());
        candidatDTO.setBio(offreCandidature.getBeneficiaire().getBio());
        candidatDTO.setCompetences(offreCandidature.getBeneficiaire().getCompetences().stream().map(Competence::getNom).collect(Collectors.toList()));
        candidatDTO.setOffreCandidatureId(offreCandidature.getId());
        candidatDTO.setCandidatureStatus(offreCandidature.getCondidatureStatus());
        if (offreCandidature.getDateCandidature() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            candidatDTO.setDateCandidature(sdf.format(offreCandidature.getDateCandidature()));
        }
        if (offreCandidature.getDateUpdated()!= null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            candidatDTO.setDateUpdated(sdf.format(offreCandidature.getDateUpdated()));
        }
        return  candidatDTO;


    }
}
