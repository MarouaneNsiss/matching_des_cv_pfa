package com.example.matching_des_cv_pfa.mappers;


import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.dto.ExperienceDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Experience;
import com.example.matching_des_cv_pfa.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BeneficiaireMapperImpl implements BeneficiaireMapper {
    private ExperienceMapper experienceMapper;
    private FileStorageService  fileStorageService;

    public BeneficiaireMapperImpl(ExperienceMapper experienceMapper, FileStorageService fileStorageService) {
        this.experienceMapper = experienceMapper;
        this.fileStorageService = fileStorageService;
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
        if (beneficiaire.getImagePath() != null) {
            try {
                // Read logo file as byte array
                Path imagePath = fileStorageService.getFilePath(beneficiaire.getImagePath(), true);
                byte[] imageBytes = Files.readAllBytes(imagePath);
                beneficiaireDTO.setImage(imageBytes);
            } catch (IOException e) {
                log.error("Error loading logo for offer: " + beneficiaire.getId(), e);
                beneficiaireDTO.setImage(null);
            }
        }
        if (beneficiaire.getCvPath() != null) {
            try {
                // Read logo file as byte array
                Path cvPath = fileStorageService.getFilePath(beneficiaire.getCvPath(), false);
                log.info("Loading CV Path: " + cvPath.toString());
                byte[] cvBytes = Files.readAllBytes(cvPath);
                beneficiaireDTO.setCv(cvBytes);
            } catch (IOException e) {
                log.error("Error loading logo for offer: " + beneficiaire.getId(), e);
                beneficiaireDTO.setCv(null);
            }

        }else{
            log.error("Error loading cv for bene: " + beneficiaire.getCvPath());
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
