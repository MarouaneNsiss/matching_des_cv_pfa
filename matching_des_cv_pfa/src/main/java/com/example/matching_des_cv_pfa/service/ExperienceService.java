package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Experience;

import java.util.List;

public interface ExperienceService {
    Experience saveExperience(Experience experience);
    List<Experience> getAllExperiences();
    List<Experience> getExperiencesByBeneficiaireId(Long beneficiaireId);
}
