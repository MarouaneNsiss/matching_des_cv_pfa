package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.Contrat;
import com.example.matching_des_cv_pfa.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Date;
import java.util.List;

//this object is for displaying the essential information about offre
//not the details
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreDTO {
    private Long id;
    private String logo;
    private String title;
    private String nom_entreprise;
    private String site;
    private String niveauEtudesRequis;
    private String niveauExperience;
    private List<Contrat> contrats;
    private List<Region>  regions;
    private List<String> competances;
    private Date dateTime;
    private String linkToDetails;

    private Long recruteurId;
}
