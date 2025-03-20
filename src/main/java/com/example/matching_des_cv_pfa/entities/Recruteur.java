package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruteur extends Utilisateur {
    private String entreprise;
    private String ville;
    private String pays;
    private String number_employees;
    private String site;
    private String jobFunction;
    @OneToMany(mappedBy = "recruteur")
    private List<Chat> chats ;

    @OneToMany(mappedBy = "expediteur")
    private List<Message> messages ;
}
