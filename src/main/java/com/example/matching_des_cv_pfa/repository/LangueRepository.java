package com.example.matching_des_cv_pfa.repository;

import com.example.matching_des_cv_pfa.entities.Langue;
import com.example.matching_des_cv_pfa.enums.LangueName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangueRepository extends JpaRepository<Langue,Long> {
    Langue findByLangueName(LangueName langueName);

}
