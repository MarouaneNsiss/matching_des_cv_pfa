package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import com.example.matching_des_cv_pfa.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
//this DTO object for displaying some essential information about candidat that
//apply for an offre
public class CandidatDTO {
    Long beneficiaireId;
    String nom;
    String prenom;
    String email;
    Gender gender;
    String bio;
    private List<String> competences;
    //this contain information about the status and also
    //this the id of condidature
    //why we need this id like for exemple if we make some modefication
    //we can easly get the correspond condidature
    private Long offreCandidatureId;
    private CandidatureStatus candidatureStatus;
    private String dateCandidature;//la date de postulation
    private String dateUpdated;//date de modification
}
