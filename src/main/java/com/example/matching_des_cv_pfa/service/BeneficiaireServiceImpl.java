package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Experience;
import com.example.matching_des_cv_pfa.entities.Tache;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import com.example.matching_des_cv_pfa.mappers.BeneficiaireMapper;
import com.example.matching_des_cv_pfa.repository.BeneficiaireRepository;
import com.example.matching_des_cv_pfa.repository.CompetenceRepository;
import com.example.matching_des_cv_pfa.repository.ExperienceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BeneficiaireServiceImpl implements BeneficiaireService {
    private BeneficiaireRepository beneficiaireRepository;
    private BeneficiaireMapper beneficiaireMapper;
    private FileStorageService fileStorageService;
    private CompetenceRepository competenceRepository;
    private ExperienceRepository experienceRepository;

    public BeneficiaireServiceImpl(BeneficiaireRepository beneficiaireRepository, BeneficiaireMapper beneficiaireMapper, FileStorageService fileStorageService, CompetenceRepository competenceRepository, ExperienceRepository experienceRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
        this.beneficiaireMapper = beneficiaireMapper;
        this.fileStorageService = fileStorageService;
        this.competenceRepository = competenceRepository;
        this.experienceRepository = experienceRepository;
    }


    @Override
    public BeneficiaireDTO saveBeneficiaire(Beneficiaire beneficiaire) {
        return this.beneficiaireMapper.fromBeneficaire(beneficiaireRepository.save(beneficiaire));
    }


    @Override
    public BeneficiaireDTO getBeneficiaireByEmail(String email) throws BeneficiaireNotFoundException {
        log.info("getBeneficiaireByEmail");
        Beneficiaire beneficiare  =  beneficiaireRepository.findByEmail(email);
        if (beneficiare == null){
            throw new BeneficiaireNotFoundException("beneficaire not found");
        }
        return this.beneficiaireMapper.fromBeneficaire(beneficiare);

    }
    @Override
    public BeneficiaireDTO createBeneficiaire(
            BeneficiaireDTO beneficiaireDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException {
        // Convert DTO to entity
        Beneficiaire beneficiaire = beneficiaireMapper.fromBeneficaireDTO(beneficiaireDTO);
        // Handle competences
        if (beneficiaire.getCompetences() != null) {
            List<Competence> savedCompetences = new ArrayList<>();
            for (Competence competence : beneficiaire.getCompetences()) {
                Competence existingCompetence = competenceRepository.findCompetenceByNom(competence.getNom());
                savedCompetences.add(existingCompetence != null ? existingCompetence : competenceRepository.save(competence));
            }
            beneficiaire.setCompetences(savedCompetences);
        } else {
            log.debug("Competences null in update operation");
            beneficiaire.setCompetences(new ArrayList<>());
        }
        //handle experiences
        if(beneficiaire.getExperiences() != null) {
            beneficiaire.getExperiences().stream().forEach(experience -> {
                experience.setBeneficiaire(beneficiaire);
                experience.getTaches().stream().forEach(tache -> {
                    tache.setExperience(experience);
                });
            });
        }

        // Save entity first to get ID
        Beneficiaire savedBeneficiaire = beneficiaireRepository.save(beneficiaire);

        // Full name for filename
        String fullName = savedBeneficiaire.getNom() + " " + savedBeneficiaire.getPrenom();

        // Handle image file
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(
                    imageFile,
                    fullName,
                    savedBeneficiaire.getId()
            );
            savedBeneficiaire.setImagePath(imagePath);
        }


        // Handle CV file
        if (cvFile != null && !cvFile.isEmpty()) {
            String cvPath = fileStorageService.storeCv(
                    cvFile,
                    fullName,
                    savedBeneficiaire.getId()
            );
            savedBeneficiaire.setCvPath(cvPath);
        }

        // Update with file paths
        savedBeneficiaire = beneficiaireRepository.save(savedBeneficiaire);

        // Convert back to DTO
        return beneficiaireMapper.fromBeneficaire(savedBeneficiaire);
    }
    @Override
    public BeneficiaireDTO updateBeneficiaire(
            Long id,
            BeneficiaireDTO beneficiaireDTO,
            MultipartFile imageFile,
            MultipartFile cvFile
    ) throws IOException, BeneficiaireNotFoundException {
        log.info("Update beneficiaire service with id " + id);
        // Find existing beneficiaire
        Beneficiaire existingBeneficiaire = beneficiaireRepository.findById(id)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiaire not found"));

        // Full name for filename
        String fullName = existingBeneficiaire.getNom() + " " + existingBeneficiaire.getPrenom();

        // Update basic information
        Beneficiaire updatedBeneficiaire = this.beneficiaireMapper.fromBeneficaireDTO(beneficiaireDTO);

        // Handle competences with careful persistence
        if (updatedBeneficiaire.getCompetences() != null && !updatedBeneficiaire.getCompetences().isEmpty()) {
            List<Competence> savedCompetences = new ArrayList<>();
            for (Competence competence : updatedBeneficiaire.getCompetences()) {
                // Check if competence already exists
                Competence existingCompetence = competenceRepository.findCompetenceByNom(competence.getNom());

                if (existingCompetence != null) {
                    // Use existing competence if found
                    savedCompetences.add(existingCompetence);
                } else {
                    // Persist new competence if not found
                    Competence persistedCompetence = competenceRepository.save(competence);
                    savedCompetences.add(persistedCompetence);
                }
            }
            existingBeneficiaire.setCompetences(savedCompetences);
        } else {
            log.debug("Competences null in update operation");
            existingBeneficiaire.setCompetences(new ArrayList<>());
        }

        if (beneficiaireDTO.getExperiences() != null) {
            // Work with the existing collection instead of replacing it
            List<Experience> existingExperiences = existingBeneficiaire.getExperiences();

            // Clear the collection (this is fine)
            existingExperiences.clear();

            // Add updated experiences to the existing collection
            for (Experience experience : updatedBeneficiaire.getExperiences()) {
                // Ensure bidirectional relationship
                experience.setBeneficiaire(existingBeneficiaire);

                if (experience.getId() != null) {
                    // Update existing experience
                    Experience existingExperience = experienceRepository.findById(experience.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Experience not found"));

                    existingExperience.setTitre(experience.getTitre());
                    existingExperience.setDescription(experience.getDescription());
                    existingExperience.setDateDebut(experience.getDateDebut());
                    existingExperience.setDateFin(experience.getDateFin());

                    // Update tasks if needed
                    if (experience.getTaches() != null) {
                        existingExperience.getTaches().clear();
                        for (Tache tache : experience.getTaches()) {
                            tache.setExperience(existingExperience);
                            existingExperience.getTaches().add(tache);
                        }
                    }

                    existingExperiences.add(existingExperience);
                } else {
                    // For new experiences
                    experience.setBeneficiaire(existingBeneficiaire);
                    if (experience.getTaches() != null) {
                        experience.getTaches().forEach(tache -> tache.setExperience(experience));
                    }
                    existingExperiences.add(experience);
                }
            }
        } else {
            // Clear experiences if DTO has none
            existingBeneficiaire.getExperiences().clear();
        }

        // Handle image file update
        if (imageFile != null && !imageFile.isEmpty()) {
            // Delete old image if exists
            if (existingBeneficiaire.getImagePath() != null) {
                fileStorageService.deleteFile(existingBeneficiaire.getImagePath(), true);
            }

            // Store new image
            String imagePath = fileStorageService.storeImage(
                    imageFile,
                    fullName,
                    existingBeneficiaire.getId()
            );
            existingBeneficiaire.setImagePath(imagePath);
        }

        // Handle CV file update
        if (cvFile != null && !cvFile.isEmpty()) {
            // Delete old CV if exists
            if (existingBeneficiaire.getCvPath() != null) {
                fileStorageService.deleteFile(existingBeneficiaire.getCvPath(), false);
            }

            // Store new CV
            String cvPath = fileStorageService.storeCv(
                    cvFile,
                    fullName,
                    existingBeneficiaire.getId()
            );
            existingBeneficiaire.setCvPath(cvPath);
        }

        // Carefully merge properties
        if (updatedBeneficiaire.getNom() != null) existingBeneficiaire.setNom(updatedBeneficiaire.getNom());
        if (updatedBeneficiaire.getPrenom() != null) existingBeneficiaire.setPrenom(updatedBeneficiaire.getPrenom());
        // Add other properties as needed
        // Avoid using BeanUtils.copyProperties as it can overwrite associations

        // Save and return
        Beneficiaire savedBeneficiaire = beneficiaireRepository.save(existingBeneficiaire);
        return beneficiaireMapper.fromBeneficaire(savedBeneficiaire);
    }
    @Override
    public BeneficiaireDTO getBeneficiare(Long beneficiareId) throws BeneficiaireNotFoundException {
        log.info("get beneficiare by id : {}", beneficiareId);
        Beneficiaire beneficiaire = this.beneficiaireRepository.findById(beneficiareId).orElseThrow(
                () -> new BeneficiaireNotFoundException("Beneficiare not found")
        );
        return this.beneficiaireMapper.fromBeneficaire(beneficiaire);
    }
    @Override
    public Page<BeneficiaireDTO> getAllBeneficiaires(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Beneficiaire> beneficiaireS = this.beneficiaireRepository.findAll(pageable);
        return beneficiaireS.map(this.beneficiaireMapper::ToBeneDTOwithoutExperiences);

    }


}
