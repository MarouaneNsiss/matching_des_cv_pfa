package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findByExperienceId(Long experienceId);  // Rechercher les tâches par ID d'expérience
}
