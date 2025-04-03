package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecruteurMapperImpl implements RecruteurMapper {
    @Override
    public Recruteur fromRecruteurDTO(RecruteurDTO recruteurDTO) {
        Recruteur recruteur= new Recruteur();
        BeanUtils.copyProperties(recruteurDTO,recruteur);
        recruteur.setRoles(Collections.singletonList(Role.Recruteur));
        return recruteur;
    }

    @Override
    public RecruteurDTO fromRecruteur(Recruteur recruteur) {
        RecruteurDTO recruteurDTO = new RecruteurDTO();
        BeanUtils.copyProperties(recruteur,recruteurDTO);
        recruteurDTO.setPassword(null);
        return recruteurDTO;
    }
}
