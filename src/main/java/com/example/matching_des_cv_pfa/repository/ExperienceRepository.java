package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByBeneficiaireId(Long beneficiaireId);  //pour rechercher par bénéficiaire
}
