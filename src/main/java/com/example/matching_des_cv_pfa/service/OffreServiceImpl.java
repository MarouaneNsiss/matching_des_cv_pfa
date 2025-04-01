package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.Specification.OffreSpecifications;
import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.dto.OffreFilterDTO;
import com.example.matching_des_cv_pfa.entities.*;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.RecruteurNotFoundException;
import com.example.matching_des_cv_pfa.mappers.OffreMapper;
import com.example.matching_des_cv_pfa.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j

public class OffreServiceImpl implements OffreService {
    private OffreRepository offreRepository;

    private CompetenceRepository competenceRepository;
    private OffreMapper offreMapper;
    private LangueRepository langueRepository;
    private RecruteurRepository recruteurRepository;
    private OffreLangueRepository offreLangueRepository;

    public OffreServiceImpl(OffreRepository offreRepository, CompetenceRepository competenceRepository, OffreMapper offreMapper, LangueRepository langueRepository,RecruteurRepository recruteurRepository,OffreLangueRepository offreLangueRepository) {
        this.offreRepository = offreRepository;
        this.competenceRepository = competenceRepository;
        this.offreMapper = offreMapper;
        this.langueRepository = langueRepository;
        this.recruteurRepository = recruteurRepository;
        this.offreLangueRepository = offreLangueRepository;
    }

    @Override
    public OffreDetailsDTO saveOffre(OffreDetailsDTO offreDetailsDTO) throws RecruteurNotFoundException {
        log.info("OffreServiceImpl saveOffre");
        Offre offre = offreMapper.fromOffreDetailsDTO(offreDetailsDTO);

        // Handle competences
        if (offre.getCompetences() != null) {
            List<Competence> savedCompetences = new ArrayList<>();
            for (Competence competence : offre.getCompetences()) {
                Competence existingCompetence = competenceRepository.findCompetenceByNom(competence.getNom());
                savedCompetences.add(existingCompetence != null ? existingCompetence : competenceRepository.save(competence));
            }
            offre.setCompetences(savedCompetences);
        }else {
            System.out.println("Competances null");
        }


        // Handle languages
        List<OffreLangue> savedOffreLangues = new ArrayList<>();
        if (offre.getOffreLangue() != null) {
            for (OffreLangue offreLangue : offre.getOffreLangue()) {
                // Check if the langue exists by enum value
                Langue existingLangue = langueRepository.findByLangueName(offreLangue.getLangue().getLangueName());
                if (existingLangue != null) {
                    offreLangue.setLangue(existingLangue);
                } else {
                    // Save new langue
                    offreLangue.setLangue(langueRepository.save(offreLangue.getLangue()));
                }

                // Set the offre reference
                offreLangue.setOffre(offre);
                savedOffreLangues.add(offreLangue);
            }
        }else{
            System.out.println("offreLangue is null");
        }
        offre.setOffreLangue(savedOffreLangues);
        // Handle recruteur
        Recruteur recruteur = recruteurRepository.findById(offreDetailsDTO.getOffreDTO().getRecruteurId())
                .orElseThrow(() -> new RecruteurNotFoundException("Recruteur not found"));
        offre.setRecruteur(recruteur);

        Offre savedOffre = this.offreRepository.save(offre);
        return this.offreMapper.ToOffreDetailsDTO(savedOffre);
    }

    @Override
    @Transactional
    public OffreDetailsDTO updateOffre(OffreDetailsDTO offreDetailsDTO) throws RecruteurNotFoundException, OffreNotFoundException {
        log.info("OffreServiceImpl updateOffre");

        // Find the existing offre to ensure it exists
        Offre existingOffre = offreRepository.findById(offreDetailsDTO.getOffreDTO().getId())
                .orElseThrow(() -> new OffreNotFoundException("Offre not found with id: " + offreDetailsDTO.getOffreDTO().getId()));

        // Delete existing offreLangue associations
        offreLangueRepository.deleteByOffre(existingOffre);

        // Map the DTO to entity
        Offre offre = offreMapper.fromOffreDetailsDTO(offreDetailsDTO);

        // Handle competences
        if (offre.getCompetences() != null) {
            List<Competence> savedCompetences = new ArrayList<>();
            for (Competence competence : offre.getCompetences()) {
                Competence existingCompetence = competenceRepository.findCompetenceByNom(competence.getNom());
                savedCompetences.add(existingCompetence != null ? existingCompetence : competenceRepository.save(competence));
            }
            offre.setCompetences(savedCompetences);
        } else {
            log.debug("Competences null in update operation");
            offre.setCompetences(new ArrayList<>());
        }

        // Handle languages
        List<OffreLangue> savedOffreLangues = new ArrayList<>();
        if (offre.getOffreLangue() != null) {
            for (OffreLangue offreLangue : offre.getOffreLangue()) {
                // Check if the langue exists by enum value
                Langue existingLangue = langueRepository.findByLangueName(offreLangue.getLangue().getLangueName());
                if (existingLangue != null) {
                    offreLangue.setLangue(existingLangue);
                } else {
                    // Save new langue
                    offreLangue.setLangue(langueRepository.save(offreLangue.getLangue()));
                }

                // Set the offre reference
                offreLangue.setOffre(offre);
                savedOffreLangues.add(offreLangue);
            }
        } else {
            log.debug("OffreLangue is null in update operation");
        }
        offre.setOffreLangue(savedOffreLangues);

        // Handle recruteur
        Recruteur recruteur = recruteurRepository.findById(offreDetailsDTO.getOffreDTO().getRecruteurId())
                .orElseThrow(() -> new RecruteurNotFoundException("Recruteur not found"));
        offre.setRecruteur(recruteur);

        Offre savedOffre = this.offreRepository.save(offre);
        return this.offreMapper.ToOffreDetailsDTO(savedOffre);
    }

    public void deleteOffre(Long id) throws OffreNotFoundException {
        log.info("OffreServiceImpl deleteOffre: {}", id);

        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new OffreNotFoundException("Offre not found with id: " + id));
        offreRepository.deleteById(id);
    }

    @Override
    public Page<OffreDTO> findAllOffre(int page, int size) {
        log.info("OffreServiceImpl findAllOffre");
        Sort sort =  Sort.by(Sort.Direction.DESC, "dateTime");
        Pageable pageable = PageRequest.of(page, size, sort);

        // This returns Page<Offre>
        Page<Offre> offrePage = offreRepository.findAll(pageable);

        // Convert to Page<OffreDTO> while keeping pagination metadata
        Page<OffreDTO> offreDTOS = offrePage.map(offre -> offreMapper.fromOffre(offre));

        return offreDTOS;
    }

    @Override
    public OffreDetailsDTO findOffreById(Long id) throws OffreNotFoundException {
        log.info ("OffreServiceImpl findOffreById : {id}",id);
        Offre offre =  this.offreRepository.findById(id).orElse(null);
        if(offre == null){
            throw new OffreNotFoundException("offre not found");
        }else{
            return this.offreMapper.ToOffreDetailsDTO(offre);
        }
    }
    @Override
    public Page<OffreDTO> getFilteredOffres(OffreFilterDTO filter,int Page ,int size) {
        log.info("OffreServiceImpl getFilteredOffres");
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        Pageable pageable = PageRequest.of(Page , size, sort);
        Specification<Offre> spec = OffreSpecifications.withFilters(filter);
        Page<Offre> offresPage = offreRepository.findAll(spec, pageable);
        Page<OffreDTO> offreDTOS = offresPage.map(offre -> offreMapper.fromOffre(offre));
        return offreDTOS;
    }

    @Override
    public Page<OffreDTO> getOffresByRecruteur(Long recruteurId, int page, int size) throws RecruteurNotFoundException {
        log.info("OffreServiceImpl getOffresByRecruteur");
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Recruteur recruteur = recruteurRepository.findById(recruteurId).orElseThrow(() -> new RecruteurNotFoundException("Recruteur not found"));
        Page<Offre> offresPage =  offreRepository.findAllByRecruteur(recruteur,pageable);
        Page<OffreDTO> offreDTOS = offresPage.map(offre -> offreMapper.fromOffre(offre));
        return offreDTOS;
    }
}
