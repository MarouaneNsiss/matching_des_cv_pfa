package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.entities.Role;
import com.example.matching_des_cv_pfa.enums.AccountStatus;
import com.example.matching_des_cv_pfa.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruteurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String bio;
    private String email;
    private String telephone;
    private String password;
    private String adresse;
    private boolean emailVerified;
    private Gender gender;

    private String ImagePath;
    private Role role;

    private String entreprise;
    private String ville;
    private String pays;
    private String number_employees;
    private String site;
    private String jobFunction;

}
