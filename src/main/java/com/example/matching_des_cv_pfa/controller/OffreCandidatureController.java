package com.example.matching_des_cv_pfa.controller;

import com.example.matching_des_cv_pfa.dto.CandidatDTO;
import com.example.matching_des_cv_pfa.dto.OffreCandidatureDTO;
import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.service.OffreCandidatureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidatures")
@AllArgsConstructor
public class OffreCandidatureController {
    private final OffreCandidatureService offreCandidatureService;

    @PostMapping("/apply/{beneficiaireId}/{offreId}")
    public ResponseEntity<OffreCandidatureDTO> applyToOffre(
            @PathVariable Long beneficiaireId,
            @PathVariable Long offreId) {
        try {
            OffreCandidatureDTO candidatureDTO = offreCandidatureService.applyTOffre(beneficiaireId, offreId);
            return new ResponseEntity<>(candidatureDTO, HttpStatus.CREATED);
        } catch (BeneficiaireNotFoundException | OffreNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status/{candidatureId}")
    public ResponseEntity<OffreCandidatureDTO> updateStatus(
            @PathVariable Long candidatureId,
            @RequestParam CandidatureStatus status) {
        try {
            OffreCandidatureDTO updatedCandidature = offreCandidatureService.updateCandidatureStatus(candidatureId, status);
            return new ResponseEntity<>(updatedCandidature, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    public ResponseEntity<Page<OffreCandidatureDTO>> getCandidaturesByBeneficiaire(
            @PathVariable Long beneficiaireId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<OffreCandidatureDTO> candidatures = offreCandidatureService.getCandidaturesByBeneficiaire(beneficiaireId, page, size);
            return new ResponseEntity<>(candidatures, HttpStatus.OK);
        } catch (BeneficiaireNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/offre/{offreId}")
    public ResponseEntity<Page<CandidatDTO>> getCandidatsByOffre(
            @PathVariable Long offreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CandidatDTO> candidats = offreCandidatureService.getCandidatsByOffre(offreId, page, size);
            return new ResponseEntity<>(candidats, HttpStatus.OK);
        } catch (OffreNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffreCandidature(@PathVariable Long id) {
        offreCandidatureService.deleteCandidature(id);
        return ResponseEntity.ok("OffreCandidature with ID " + id + " deleted successfully!");
    }

}
