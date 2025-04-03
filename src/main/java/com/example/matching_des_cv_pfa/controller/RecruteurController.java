package com.example.matching_des_cv_pfa.controller;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import com.example.matching_des_cv_pfa.service.RecruteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/recruteur")
public class RecruteurController {

    private final RecruteurService recruteurService;

    @Autowired
    public RecruteurController(RecruteurService recruteurService) {
        this.recruteurService = recruteurService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruteurDTO> updateRecruteur(
            @PathVariable Long id,
            @RequestPart("recruteur") RecruteurDTO recruteurDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile profileImage) throws RecruteurNotFoundException {

        RecruteurDTO updatedRecruteur = recruteurService.updateRecruteur(id, recruteurDto, profileImage);
        return ResponseEntity.ok(updatedRecruteur);
    }
}
