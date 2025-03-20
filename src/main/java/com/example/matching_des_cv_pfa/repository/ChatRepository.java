package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Chat;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByBeneficiaireId(Long beneficiaireId);
    List<Chat> findByRecruteurId(Long recruteurId);
    Chat findByBeneficiaireIdAndRecruteurId(Long beneficiaireId,Long  recruteurId);
}
