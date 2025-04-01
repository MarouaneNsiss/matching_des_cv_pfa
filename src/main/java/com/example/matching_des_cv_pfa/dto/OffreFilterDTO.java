package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.Contrat;
import com.example.matching_des_cv_pfa.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//this object for beneficaire filter
public class OffreFilterDTO {
    private List<String> competences;
    private List<Region> regions;
    private List<Contrat> contrats;
    private String niveauExperience;
    private String niveauEtudesRequis;
    private List<String> langues;
    private String keyword; // For searching in title/description
    private Date fromDate;
    private Date toDate;
    private String company; // Filter by company
}