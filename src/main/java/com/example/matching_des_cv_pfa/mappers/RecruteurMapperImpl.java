package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Role;
import com.example.matching_des_cv_pfa.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Service
@Slf4j
public class RecruteurMapperImpl implements RecruteurMapper {
    private FileStorageService fileStorageService;
    @Autowired
    public RecruteurMapperImpl(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @Override
    public Recruteur fromRecruteurDTO(RecruteurDTO recruteurDTO) {
        Recruteur recruteur= new Recruteur();
        BeanUtils.copyProperties(recruteurDTO,recruteur);
        recruteur.setRoles(Collections.singletonList(Role.Recruteur));
        return recruteur;
    }

    @Override
    public RecruteurDTO fromRecruteur(Recruteur recruteur){
        RecruteurDTO recruteurDTO = new RecruteurDTO();
        BeanUtils.copyProperties(recruteur,recruteurDTO);
        recruteurDTO.setPassword(null);
        if(recruteur.getImagePath()!=null){
            try {
                Path path = fileStorageService.getFilePath(recruteur.getImagePath(), true);
                byte[] imageBytes = Files.readAllBytes(path);
                recruteurDTO.setImage(imageBytes);
            }catch (IOException e){
                log.error("Error loading image for recruteur: " + recruteur.getId(), e);
                recruteurDTO.setImage(null);
            }
        }
        return recruteurDTO;
    }
}
