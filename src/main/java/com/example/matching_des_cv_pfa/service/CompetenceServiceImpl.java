package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompetenceServiceImpl implements CompetenceService{
    private CompetenceRepository competenceRepository;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    @Override
    public Competence saveCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    @Override
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }
}
