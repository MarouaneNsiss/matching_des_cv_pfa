package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Experience;
import com.example.matching_des_cv_pfa.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExperenceServiceImpl implements ExperienceService{
    private ExperienceRepository experienceRepository;

    public ExperenceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public List<Experience> getExperiencesByBeneficiaireId(Long beneficiaireId) {
        return experienceRepository.findByBeneficiaireId(beneficiaireId);
    }
}
