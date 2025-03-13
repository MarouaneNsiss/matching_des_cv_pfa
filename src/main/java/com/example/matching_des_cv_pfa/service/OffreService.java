package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;


public interface OffreService {
    Offre saveOffre(Offre offre);
    Offre updateOffre(Offre offre);
    void deleteOffre(Long id);
    Page<OffreDTO> findAllOffre(int page, int size);
    Offre findOffreById(Long id) throws OffreNotFoundException;


}
