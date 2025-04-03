package com.example.matching_des_cv_pfa.entities;

import com.example.matching_des_cv_pfa.enums.Contrat;
import com.example.matching_des_cv_pfa.enums.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logo;
    private String title;
    private String niveauEtudesRequis;
    private String description;
    private String niveauExperience;
    //this add just for web scraped jobs
    private String linkToDetails;
    private Date dateTime;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Competence> competences;

    @ElementCollection(targetClass = Region.class)
    @CollectionTable(name = "offre_Region", joinColumns = @JoinColumn(name = "offre_id"))
    private List<Region> regions;

    @ElementCollection(targetClass = Contrat.class)
    @CollectionTable(name = "offre_Contrat", joinColumns = @JoinColumn(name = "offre_id"))
    private List<Contrat> contrats;
    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OffreLangue> offreLangue;;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruteur_id", nullable = true)
    Recruteur recruteur;

}
