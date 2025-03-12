package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Competence;

import java.util.List;

public interface CompetenceService {
     Competence saveCompetence(Competence competence);

     List<Competence> getAllCompetences();
}
