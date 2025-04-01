package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.ExperienceDTO;
import com.example.matching_des_cv_pfa.entities.Experience;

public interface ExperienceMapper {
    ExperienceDTO fromExperience(Experience experience);
    Experience fromExperienceDTO(ExperienceDTO experienceDTO);
}
