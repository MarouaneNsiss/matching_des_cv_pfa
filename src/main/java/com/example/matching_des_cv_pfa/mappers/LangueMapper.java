package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.LangueDTO;
import com.example.matching_des_cv_pfa.entities.OffreLangue;

public interface LangueMapper {
    LangueDTO fromOffreLangue(OffreLangue offreLangue);

    OffreLangue fromLangueDTO(LangueDTO langueDTO);
}
