package com.example.matching_des_cv_pfa.mappers;


import com.example.matching_des_cv_pfa.dto.BeneficiareDTO;
import com.example.matching_des_cv_pfa.dto.ExperienceDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Experience;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficiaireMapperImpl implements BeneficiaireMapper {
    private ExperienceMapper experienceMapper;

    public BeneficiaireMapperImpl(ExperienceMapper experienceMapper) {
        this.experienceMapper = experienceMapper;
    }

    @Override
    public BeneficiareDTO fromBeneficaire(Beneficiaire beneficiaire) {
        BeneficiareDTO beneficiaireDTO = new BeneficiareDTO();
        BeanUtils.copyProperties(beneficiaire,beneficiaireDTO);
        List<ExperienceDTO> experiencesDTO = beneficiaire.getExperiences().stream().map(
                experience -> this.experienceMapper.fromExperience(experience)
        ).collect(Collectors.toList());
        beneficiaireDTO.setExperiences(experiencesDTO);
        //get just names of competance
        if(beneficiaireDTO.getExperiences() != null){
            List<String> competances = beneficiaire.getCompetences().stream().map(
                    competence -> competence.getNom()
            ).collect(Collectors.toList());
            beneficiaireDTO.setCompetences(competances);
        }



        return beneficiaireDTO;
    }

    @Override
    public Beneficiaire fromBeneficaireDTO(BeneficiareDTO beneficiareDTO) {
        Beneficiaire beneficiare = new Beneficiaire();
        BeanUtils.copyProperties(beneficiareDTO,beneficiare);
        if (beneficiareDTO.getCompetences() != null) {
            List<Competence> competenceList = beneficiareDTO.getCompetences().stream().map(comp -> {
                Competence competence = new Competence();
                competence.setNom(comp);
                return competence;
            }).collect(Collectors.toList());
            beneficiare.setCompetences(competenceList);
        } else {
            beneficiare.setCompetences(new ArrayList<>());
        }
        if(beneficiareDTO.getExperiences() != null) {
            List<Experience> experienceList = beneficiareDTO.getExperiences().stream().map(
                    experienceDTO -> this.experienceMapper.fromExperienceDTO(experienceDTO)

            ).collect(Collectors.toList());
            beneficiare.setExperiences(experienceList);
        }
        return beneficiare;
    }
}
