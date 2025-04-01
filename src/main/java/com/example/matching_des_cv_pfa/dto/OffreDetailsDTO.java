package com.example.matching_des_cv_pfa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//this object used for displaying offre details
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreDetailsDTO {
    private OffreDTO offreDTO;
    private String Description;
    private List<LangueDTO> langueDTOList;
    String ville;
}
