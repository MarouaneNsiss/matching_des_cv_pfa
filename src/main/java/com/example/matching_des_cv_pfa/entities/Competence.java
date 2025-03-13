package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Beneficiaire> beneficiaires;

    @ManyToMany(mappedBy = "competences",fetch = FetchType.LAZY)
    private List<Offre> offres;
}
