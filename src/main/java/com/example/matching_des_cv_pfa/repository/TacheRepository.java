package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findByExperienceId(Long experienceId);  // Rechercher les tâches par ID d'expérience
}
