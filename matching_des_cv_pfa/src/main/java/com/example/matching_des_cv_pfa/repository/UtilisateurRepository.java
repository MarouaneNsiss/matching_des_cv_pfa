package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByEmail(String email);
    boolean existsByEmail(String email);

}
