package com.example.matching_des_cv_pfa.repository;


import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.OffreCandidature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreCandidatureRepository extends JpaRepository<OffreCandidature,Long> {
    boolean existsByBeneficiaireAndOffre(Beneficiaire beneficiaire, Offre offre);
    Page<OffreCandidature> findAllByBeneficiaire(Beneficiaire beneficiaire, Pageable pageable);

    Page<OffreCandidature> findAllByOffre(Offre offre, Pageable pageable);
}
