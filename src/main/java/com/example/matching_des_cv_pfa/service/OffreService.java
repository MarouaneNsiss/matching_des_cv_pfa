package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.dto.OffreFilterDTO;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OffreService {
    OffreDetailsDTO saveOffre(OffreDetailsDTO offredetailsDTO) throws RecruteurNotFoundException;

    OffreDetailsDTO updateOffre(OffreDetailsDTO offreDetailsDTO) throws RecruteurNotFoundException, OffreNotFoundException;

    void deleteOffre(Long id) throws OffreNotFoundException;
    Page<OffreDTO> findAllOffre(int page, int size);
    OffreDetailsDTO findOffreById(Long id) throws OffreNotFoundException;


    Page<OffreDTO> getFilteredOffres(OffreFilterDTO filter, int page, int size);
    Page<OffreDTO> getOffresByRecruteur(Long RecruteurId, int page, int size) throws RecruteurNotFoundException;
}
