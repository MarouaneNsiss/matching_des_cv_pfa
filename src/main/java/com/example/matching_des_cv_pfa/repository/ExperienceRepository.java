package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByBeneficiaireId(Long beneficiaireId);  //pour rechercher par bénéficiaire
}
