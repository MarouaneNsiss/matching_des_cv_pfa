package com.example.matching_des_cv_pfa.entities;

import com.example.matching_des_cv_pfa.enums.LangueLevel;
import com.example.matching_des_cv_pfa.enums.LangueName;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Langue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("Langue_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    LangueName langueName;
    @OneToMany(mappedBy = "langue")
    private List<OffreLangue> offreLangueList;

}
