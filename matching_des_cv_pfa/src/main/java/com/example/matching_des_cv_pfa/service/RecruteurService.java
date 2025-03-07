package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Recruteur;

public interface RecruteurService {
    Recruteur getRecruteurByEmail(String email);
    Recruteur saveRecruteur( Recruteur recruteur);
}
