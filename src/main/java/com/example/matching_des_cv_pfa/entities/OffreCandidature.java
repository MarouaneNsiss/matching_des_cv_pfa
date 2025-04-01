package com.example.matching_des_cv_pfa.entities;


import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreCandidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date dateCandidature;//stores when the condidture created
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;//stores when the condidature's status changed

    @Enumerated(EnumType.STRING)
    private CandidatureStatus condidatureStatus;//    PENDING, ACCEPTED, REJECTED

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Beneficiaire beneficiaire;

    @ManyToOne
    @JoinColumn(name = "offre_id", nullable = false)
    private Offre offre;




}
