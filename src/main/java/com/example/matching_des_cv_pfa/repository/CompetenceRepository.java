package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Competence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Competence findCompetenceByNom(String nom);

}
