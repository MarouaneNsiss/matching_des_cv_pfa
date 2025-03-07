package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire,Long> {
    Beneficiaire findByEmail(String email);
}
