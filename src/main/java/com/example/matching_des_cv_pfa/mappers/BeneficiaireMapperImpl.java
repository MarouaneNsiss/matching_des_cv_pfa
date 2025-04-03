package com.example.matching_des_cv_pfa.mappers;


import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.dto.ExperienceDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Experience;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficiaireMapperImpl implements BeneficiaireMapper {
    private ExperienceMapper experienceMapper;

    public BeneficiaireMapperImpl(ExperienceMapper experienceMapper) {
        this.experienceMapper = experienceMapper;
    }

    @Override
    public BeneficiaireDTO fromBeneficaire(Beneficiaire beneficiaire) {
        BeneficiaireDTO beneficiaireDTO = new BeneficiaireDTO();
        BeanUtils.copyProperties(beneficiaire,beneficiaireDTO);
        List<ExperienceDTO> experiencesDTO = beneficiaire.getExperiences().stream().map(
                experience -> this.experienceMapper.fromExperience(experience)
        ).collect(Collectors.toList());
        beneficiaireDTO.setExperiences(experiencesDTO);
        //get just names of competance
        if(beneficiaire.getCompetences() != null){
            List<String> competances = beneficiaire.getCompetences().stream().map(
                    competence -> competence.getNom()
            ).collect(Collectors.toList());
            beneficiaireDTO.setCompetences(competances);
        }



        return beneficiaireDTO;
    }

    @Override
    public Beneficiaire fromBeneficaireDTO(BeneficiaireDTO beneficiaireDTO) {
        Beneficiaire beneficiare = new Beneficiaire();
        BeanUtils.copyProperties(beneficiaireDTO,beneficiare);
        if (beneficiaireDTO.getCompetences() != null) {
            List<Competence> competenceList = beneficiaireDTO.getCompetences().stream().map(comp -> {
                Competence competence = new Competence();
                competence.setNom(comp);
                return competence;
            }).collect(Collectors.toList());
            beneficiare.setCompetences(competenceList);
        } else {
            beneficiare.setCompetences(new ArrayList<>());
        }
        if(beneficiaireDTO.getExperiences() != null) {
            List<Experience> experienceList = beneficiaireDTO.getExperiences().stream().map(
                    experienceDTO -> this.experienceMapper.fromExperienceDTO(experienceDTO)

            ).collect(Collectors.toList());
            beneficiare.setExperiences(experienceList);
        }
        return beneficiare;
    }
    @Override
    public BeneficiaireDTO ToBeneDTOwithoutExperiences(Beneficiaire beneficiaire){
        BeneficiaireDTO beneficiaireDTO = new BeneficiaireDTO();
        BeanUtils.copyProperties(beneficiaire,beneficiaireDTO);
        //get just names of competance
        if(beneficiaire.getCompetences() != null){
            List<String> competances = beneficiaire.getCompetences().stream().map(
                    competence -> competence.getNom()
            ).collect(Collectors.toList());
            beneficiaireDTO.setCompetences(competances);
        }
        return beneficiaireDTO;

    }
}
