package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.Contrat;
import com.example.matching_des_cv_pfa.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreDTO {
    private Long id;
    private String Logo;
    private String title;
    private String nom_entreprise;
    private String site;
    private String Niveau_etudes_requis;
    private String niveau_experience;
    private List<Contrat> contrats;
    private List<Region>  regions;
    private List<String> competances;
    private Date datetime;
    private String linkToDetails;
}
