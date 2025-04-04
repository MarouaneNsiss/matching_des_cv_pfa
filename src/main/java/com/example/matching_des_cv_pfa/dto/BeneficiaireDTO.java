package com.example.matching_des_cv_pfa.dto;


import com.example.matching_des_cv_pfa.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaireDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String bio;
    private String email;
    private String telephone;
    private String adresse;

    private byte[] image;
    private Gender gender;

    private byte[] cv;
    private List<ExperienceDTO> experiences;
    private List<String> competences;

}
