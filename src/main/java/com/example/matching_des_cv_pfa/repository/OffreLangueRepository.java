package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.OffreLangue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreLangueRepository extends JpaRepository<OffreLangue,Long> {
    void deleteByOffre(Offre offre);
}
