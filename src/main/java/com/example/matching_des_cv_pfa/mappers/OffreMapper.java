package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.entities.Offre;

public interface OffreMapper {
    OffreDTO fromOffre(Offre offre);
    Offre fromOffreDTO(OffreDTO offreDTO);
    OffreDetailsDTO ToOffreDetailsDTO(Offre offre);
    Offre fromOffreDetailsDTO(OffreDetailsDTO offreDetailsDTO);

}
