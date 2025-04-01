package com.example.matching_des_cv_pfa.entities;

import com.example.matching_des_cv_pfa.enums.LangueLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OffreLangue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    @ManyToOne
    @JoinColumn(name = "langue_id")
    private Langue langue;

    @Enumerated(EnumType.STRING)
    private LangueLevel level;
}
