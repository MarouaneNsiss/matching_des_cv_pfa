package com.example.matching_des_cv_pfa.controller;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.dto.OffreFilterDTO;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import com.example.matching_des_cv_pfa.service.OffreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/offres")
@Slf4j
public class OffreController {

    private OffreService offreService;

    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }



    @GetMapping()
    public ResponseEntity<Page<OffreDTO>> getAllOffres(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        log.debug("getAllOffres");
        Page<OffreDTO> offres =  this.offreService.findAllOffre(page,size);
        return ResponseEntity.ok(offres);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<OffreDetailsDTO> getOffre(@PathVariable Long id) throws OffreNotFoundException {
        log.info("getOffreById {id}",id);
        OffreDetailsDTO offreDetailsDTO = this.offreService.findOffreById(id);
        return ResponseEntity.ok(offreDetailsDTO);
    }
    @PostMapping
    public ResponseEntity<OffreDetailsDTO> createOffre(@RequestBody OffreDetailsDTO offreDetailsDTO) throws RecruteurNotFoundException {
        log.info("createOffre {}", offreDetailsDTO);
        OffreDetailsDTO created = this.offreService.saveOffre(offreDetailsDTO);
        return ResponseEntity.created(URI.create("api/offres/"+created.getOffreDTO() .getId())).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OffreDetailsDTO> updateOffre(@PathVariable Long id, @RequestBody OffreDetailsDTO offreDetailsDTO) throws RecruteurNotFoundException, OffreNotFoundException {
        log.info("updateOffre {}", offreDetailsDTO);
        if (offreDetailsDTO.getOffreDTO().getId()== null) {
            offreDetailsDTO.getOffreDTO().setId(id);

        }
        OffreDetailsDTO updatedOffre = this.offreService.updateOffre(offreDetailsDTO);
        return ResponseEntity.ok(updatedOffre);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Offre> deleteOffre(@PathVariable Long id) throws OffreNotFoundException {
        log.info("deleteOffre {}", id);
        this.offreService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<OffreDTO>> getFilteredOffres(
            @RequestBody OffreFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Page<OffreDTO> results = offreService.getFilteredOffres(filterDTO,page,size);

        return ResponseEntity.ok(results);
    }
    @GetMapping("/recruteur/{recruteurId}")
    public ResponseEntity<Page<OffreDTO>> getoffresByRecruteur(
            @PathVariable Long recruteurId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws RecruteurNotFoundException {
        log.info("getoffresByRecruteur");
        Page<OffreDTO> results = offreService.getOffresByRecruteur(recruteurId,page,size);
        return ResponseEntity.ok(results);
    }

}
