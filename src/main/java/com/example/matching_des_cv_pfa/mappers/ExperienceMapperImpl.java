package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.ExperienceDTO;
import com.example.matching_des_cv_pfa.dto.TacheDTO;
import com.example.matching_des_cv_pfa.entities.Experience;
import com.example.matching_des_cv_pfa.entities.Tache;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceMapperImpl implements ExperienceMapper {
    private TacheMapper tacheMapper;

    public ExperienceMapperImpl(TacheMapper tacheMapper) {
        this.tacheMapper = tacheMapper;
    }

    @Override
    public ExperienceDTO fromExperience(Experience experience) {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        BeanUtils.copyProperties(experience,experienceDTO);
        if (experience.getDateDebut() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            experienceDTO.setDateDebut(sdf.format(experience.getDateDebut()));
        }

        if (experience.getDateFin() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            experienceDTO.setDateFin(sdf.format(experience.getDateFin()));
        }
        if(experience.getTaches()!=null){
        List<TacheDTO> tacheDTOS = experience.getTaches().stream().map(
                tache -> this.tacheMapper.fromTache(tache)
        ).collect(Collectors.toList());
        experienceDTO.setTaches(tacheDTOS);
        }
        return experienceDTO;
    }

    @Override
    public Experience fromExperienceDTO(ExperienceDTO experienceDTO) {
        Experience experience = new Experience();
        BeanUtils.copyProperties(experienceDTO,experience);
        try {
            if (experienceDTO.getDateDebut() != null && !experienceDTO.getDateDebut().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                experience.setDateDebut(sdf.parse(experienceDTO.getDateDebut()));
            }

            if (experienceDTO.getDateFin() != null && !experienceDTO.getDateFin().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                experience.setDateFin(sdf.parse(experienceDTO.getDateFin()));
            }
        } catch (ParseException e) {
            // Handle parsing exception - you might want to log this or handle it differently
            throw new RuntimeException("Error parsing date", e);
        }
        if(experienceDTO.getTaches() != null) {
            List<Tache> tacheList = experienceDTO.getTaches().stream().map(
                    tacheDTO -> this.tacheMapper.fromTachDTO(tacheDTO)
            ).collect(Collectors.toList());
            experience.setTaches(tacheList);
        }
        return experience;
    }
}
