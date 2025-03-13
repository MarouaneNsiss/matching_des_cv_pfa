package com.example.matching_des_cv_pfa.controller;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.service.OffreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public  ResponseEntity<Offre> getOffre(@PathVariable Long id) throws OffreNotFoundException {
        log.info("getOffreById {id}",id);
        Offre offre = this.offreService.findOffreById(id);
        return ResponseEntity.ok(offre);
    }
    @PostMapping
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre) {
        log.info("createOffre {}", offre);
        Offre created = this.offreService.saveOffre(offre);
        return ResponseEntity.created(URI.create("api/offres/"+created.getId())).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable Long id, @RequestBody Offre offre) {
        log.info("updateOffre {}", offre);
        if (offre.getId() == null) {
            offre.setId(id);
        }
        Offre updatedOffre = this.offreService.updateOffre(offre);
        return ResponseEntity.ok(updatedOffre);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Offre> deleteOffre(@PathVariable Long id) {
        log.info("deleteOffre {}", id);
        this.offreService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }

}
