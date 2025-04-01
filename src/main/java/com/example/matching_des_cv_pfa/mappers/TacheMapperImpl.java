package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.TacheDTO;
import com.example.matching_des_cv_pfa.entities.Tache;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TacheMapperImpl implements TacheMapper {
    @Override
    public TacheDTO fromTache(Tache tache) {
        TacheDTO tacheDTO = new TacheDTO();
        BeanUtils.copyProperties(tache, tacheDTO);
        return tacheDTO;
    }

    @Override
    public Tache fromTachDTO(TacheDTO tacheDTO) {
        Tache tache = new Tache();
        BeanUtils.copyProperties(tacheDTO, tache);
        return tache;
    }
}
