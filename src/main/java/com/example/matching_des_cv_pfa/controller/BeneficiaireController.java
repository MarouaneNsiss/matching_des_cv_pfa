package com.example.matching_des_cv_pfa.controller;


import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import com.example.matching_des_cv_pfa.service.BeneficiaireService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/beneficiaire")
@AllArgsConstructor
@Slf4j
public class BeneficiaireController {
    private BeneficiaireService beneficiareService;


    @GetMapping("/beneficiareByemail")
    public ResponseEntity<BeneficiaireDTO> getBeneficiaireByEmail(@RequestParam String email) throws BeneficiaireNotFoundException {
        log.info("getBeneficiaireByEmail Controller {}", email);
        return ResponseEntity.ok(this.beneficiareService.getBeneficiaireByEmail(email));
    }
    // Create a new beneficiaire with image and CV
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BeneficiaireDTO> createBeneficiaire(
            @RequestPart(value = "beneficiaire",required = false) BeneficiaireDTO beneficiaireDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "cvFile", required = false) MultipartFile cvFile
    ) throws IOException {
        log.info("Creating Beneficiaire: {}", beneficiaireDTO.getNom());

        BeneficiaireDTO createdBeneficiaire = beneficiareService.createBeneficiaire(beneficiaireDTO, imageFile, cvFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeneficiaire);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaireDTO> getBeneficiaireById(@PathVariable Long id) throws BeneficiaireNotFoundException {
        log.info("getBeneficiaireById Controller");
        return ResponseEntity.ok(this.beneficiareService.getBeneficiare(id));
    }
    @PutMapping(value = "/{beneficiareId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BeneficiaireDTO> updateBeneficiaire(
            @PathVariable Long beneficiareId,
            @RequestPart("beneficiaire") BeneficiaireDTO beneficiaireDTO,
            @RequestPart("cvFile") MultipartFile cvFile,
            @RequestParam("imageFile") MultipartFile imageFile) throws BeneficiaireNotFoundException, IOException {
        log.info("updateBeneficiaire Controller");
        BeneficiaireDTO updatedBeneficiaireDTO =  this.beneficiareService.updateBeneficiaire(
                beneficiareId,
                beneficiaireDTO,
                imageFile,
                cvFile
        );
        return ResponseEntity.ok(updatedBeneficiaireDTO);

    }
    @GetMapping("/beneficiaires")
    public ResponseEntity<Page<BeneficiaireDTO>> getAllBeneficiaires(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BeneficiaireDTO> beneficiaires = this.beneficiareService.getAllBeneficiaires(page, size);
        return new ResponseEntity<>(beneficiaires, HttpStatus.OK);
    }


}
