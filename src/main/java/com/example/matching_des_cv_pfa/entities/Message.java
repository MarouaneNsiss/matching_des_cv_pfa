package com.example.matching_des_cv_pfa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contenu;
    private String Objet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;


    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "expediteur_beneficiaire_id", nullable = true)
    private Beneficiaire expediteurBeneficiaire;

    @ManyToOne
    @JoinColumn(name = "expediteur_recruteur_id", nullable = true)
    private Recruteur expediteurRecruteur;

    @OneToMany(mappedBy = "message")
    private List<Attachement> attachements;
}
