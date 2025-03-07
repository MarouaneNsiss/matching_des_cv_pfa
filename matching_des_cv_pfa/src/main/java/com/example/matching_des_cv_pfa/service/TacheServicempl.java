package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Tache;
import com.example.matching_des_cv_pfa.repository.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TacheServicempl implements TacheService{
    private TacheRepository tacheRepository;

    public TacheServicempl(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    @Override
    public Tache saveTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public List<Tache> getTachesByExperienceId(Long experienceId) {
        return tacheRepository.findByExperienceId(experienceId);
    }

    @Override
    public Optional<Tache> getTacheById(Long id) {
        return tacheRepository.findById(id);
    }
}
