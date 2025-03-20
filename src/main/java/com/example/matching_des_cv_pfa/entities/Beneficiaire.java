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
public class Beneficiaire extends Utilisateur {

    @Lob
    @Column(name = "cv", columnDefinition = "LONGBLOB") // Stockage optimisé pour un fichier CV
    private byte[] cv;

    @OneToMany(mappedBy = "beneficiaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Experience> experiences;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Competence> competences;
    @OneToMany(mappedBy = "beneficiaire")
    private List<Chat> chats ;

    @OneToMany(mappedBy = "expediteur")
    private List<Message> messages ;
}
