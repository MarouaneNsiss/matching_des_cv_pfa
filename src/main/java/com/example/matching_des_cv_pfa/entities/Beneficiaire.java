package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiaire extends Utilisateur {

    private String cvPath;

    @OneToMany(mappedBy ="beneficiaire",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Experience> experiences=new ArrayList<>();

    @ManyToMany( fetch = FetchType.LAZY)
    private List<Competence> competences = new ArrayList<>();;


}
