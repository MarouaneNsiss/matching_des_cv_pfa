package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import com.example.matching_des_cv_pfa.mappers.RecruteurMapper;
import com.example.matching_des_cv_pfa.repository.RecruteurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class RecruteurServiceImpl implements RecruteurService{
    private RecruteurRepository recruteurRepository;
    private FileStorageService fileStorageService;
    private RecruteurMapper recruteurMapper;

    public RecruteurServiceImpl(RecruteurRepository recruteurRepository, FileStorageService fileStorageService, RecruteurMapper recruteurMapper) {
        this.recruteurRepository = recruteurRepository;
        this.fileStorageService = fileStorageService;
        this.recruteurMapper = recruteurMapper;
    }


    @Override    public Recruteur getRecruteurByEmail(String email) {

            return recruteurRepository.findByEmail(email);
    }

    @Override
    public Recruteur saveRecruteur(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }
    @Override
    public RecruteurDTO updateRecruteur(Long id, RecruteurDTO recruteurDTO, MultipartFile imageFile) throws RecruteurNotFoundException {
        Recruteur recruteur = recruteurRepository.findById(id).orElseThrow(
                () -> new RecruteurNotFoundException("Recruteur not found with id " + id)
        );
        BeanUtils.copyProperties(recruteurDTO, recruteur);

        // Handle image file update
        String fullName = recruteur.getPrenom() + " " + recruteur.getNom();
        // Delete old image if exists
        if (imageFile != null && !imageFile.isEmpty()) {
            if (recruteur.getImagePath() != null) {
                fileStorageService.deleteFile(recruteur.getImagePath(), true);
            }

            // Store new image
            String imagePath = fileStorageService.storeImage(
                    imageFile,
                    fullName,
                    recruteur.getId()
            );
            recruteur.setImagePath(imagePath);


        }
        return this.recruteurMapper.fromRecruteur(recruteurRepository.save(recruteur));
    }

}
