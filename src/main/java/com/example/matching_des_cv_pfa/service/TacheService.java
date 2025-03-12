package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Tache;

import java.util.List;
import java.util.Optional;

public interface TacheService {
    Tache saveTache(Tache tache);
     List<Tache> getAllTaches() ;


     List<Tache> getTachesByExperienceId(Long experienceId);


     Optional<Tache> getTacheById(Long id) ;

}
