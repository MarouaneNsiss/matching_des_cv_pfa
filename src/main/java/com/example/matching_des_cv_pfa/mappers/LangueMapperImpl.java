package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.LangueDTO;
import com.example.matching_des_cv_pfa.entities.Langue;
import com.example.matching_des_cv_pfa.entities.OffreLangue;
import org.springframework.stereotype.Service;

@Service
public class LangueMapperImpl implements LangueMapper {

    @Override
    public LangueDTO fromOffreLangue(OffreLangue offreLangue) {
        LangueDTO langueDTO = new LangueDTO();
        langueDTO.setLangueName(offreLangue.getLangue().getLangueName());
        langueDTO.setLangueLevel(offreLangue.getLevel());
        return langueDTO;
    }
    @Override
    public OffreLangue fromLangueDTO(LangueDTO langueDTO){
        OffreLangue offreLangue = new OffreLangue();
        Langue langue = new Langue();
        langue.setLangueName(langueDTO.getLangueName());
        offreLangue.setLangue(langue);
        offreLangue.setLevel(langueDTO.getLangueLevel());
        return offreLangue;
    }
}
