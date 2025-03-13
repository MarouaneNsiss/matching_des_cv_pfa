package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends JpaRepository<Offre,Long> {



}
