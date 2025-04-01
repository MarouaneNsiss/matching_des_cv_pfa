package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface OffreRepository extends JpaRepository<Offre,Long>, JpaSpecificationExecutor<Offre> {
    Page<Offre> findAllByRecruteur(Recruteur recruteur, Pageable pageable);



}
