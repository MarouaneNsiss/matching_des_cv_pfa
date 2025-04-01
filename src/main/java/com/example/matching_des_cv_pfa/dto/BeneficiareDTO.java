package com.example.matching_des_cv_pfa.dto;


import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Experience;
import com.example.matching_des_cv_pfa.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiareDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String bio;
    private String email;
    private String telephone;
    private String adresse;

    private String imagePath;
    private Gender gender;

    private String cv;
    private List<ExperienceDTO> experiences;
    private List<String> competences;

}
