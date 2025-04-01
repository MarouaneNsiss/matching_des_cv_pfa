package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreCandidatureDTO {
    private Long id;
    private Long beneficiaireId;
    private Long OffreId;
    private CandidatureStatus candidatureStatus;
    private String dateCandidature;//la date de postulation
    private String dateUpdated;//date de modification
    private String title;
    private String entreprise;
}
