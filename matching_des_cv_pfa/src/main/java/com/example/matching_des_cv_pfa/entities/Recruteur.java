package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruteur extends Utilisateur {
    private String entreprise;
    private String adresse;
    private String ville;
    private String pays;
    private int number_employees;
    private String site;
    private String jobFunction;

}
