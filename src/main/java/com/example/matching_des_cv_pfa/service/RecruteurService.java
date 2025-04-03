package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface RecruteurService {
    Recruteur getRecruteurByEmail(String email);
    Recruteur saveRecruteur( Recruteur recruteur);

    RecruteurDTO updateRecruteur(Long id, RecruteurDTO recruteurDTO, MultipartFile imageFile) throws RecruteurNotFoundException;
}
