package com.example.matching_des_cv_pfa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {
    private Long id;
    private String titre;
    private String nomEntreprise;
    private String description;
    private String dateDebut;
    private String dateFin;
    private List<TacheDTO> taches;
}
