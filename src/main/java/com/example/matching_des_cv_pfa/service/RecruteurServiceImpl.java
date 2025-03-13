package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruteurServiceImpl implements RecruteurService{
    private RecruteurRepository recruteurRepository;

    public RecruteurServiceImpl(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

        @Override    public Recruteur getRecruteurByEmail(String email) {

            return recruteurRepository.findByEmail(email);
    }

    @Override
    public Recruteur saveRecruteur(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }
}
