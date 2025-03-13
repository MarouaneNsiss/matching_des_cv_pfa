package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.mappers.OffreMapper;
import com.example.matching_des_cv_pfa.repository.CompetenceRepository;
import com.example.matching_des_cv_pfa.repository.OffreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j

public class OffreServiceImpl implements OffreService {
    private OffreRepository offreRepository;

    private CompetenceRepository competenceRepository;
    private OffreMapper offreMapper;

    public OffreServiceImpl(OffreRepository offreRepository, CompetenceRepository competenceRepository,OffreMapper offreMapper) {
        this.offreRepository = offreRepository;
        this.competenceRepository = competenceRepository;
        this.offreMapper = offreMapper;
    }

    @Override
    public Offre saveOffre(Offre offre) {
        log.info ("OffreServiceImpl saveOffre");
        //check that the competance exit on database if not save it then add the saved competance to offre
        //this prevent duplicate competance
        List<Competence> savedCompetences = new ArrayList<>();
        for(Competence competence : offre.getCompetences()){
            Competence existingCompetance =  competenceRepository.findCompetenceByNom(competence.getNom());
            if(existingCompetance != null){
                savedCompetences.add(existingCompetance);
            }else{
                savedCompetences.add(competenceRepository.save(competence));
            }
        }
        offre.setCompetences(savedCompetences);
        Offre savedOffre =  this.offreRepository.save(offre);
        return savedOffre;
    }

    @Override
    public Offre updateOffre(Offre offre) {
        log.info ("OffreServiceImpl updateOffre");
        Offre updatedOffre  = this.offreRepository.save(offre);
        return updatedOffre;

    }

    @Override
    public void deleteOffre(Long id) {
        log.info ("OffreServiceImpl deleteOffre");
        this.offreRepository.deleteById(id);

    }

    @Override
    public Page<OffreDTO> findAllOffre(int page, int size) {
        log.info("OffreServiceImpl findAllOffre");
        Pageable pageable = PageRequest.of(page, size);

        // This returns Page<Offre>
        Page<Offre> offrePage = offreRepository.findAll(pageable);

        // Convert to Page<OffreDTO> while keeping pagination metadata
        Page<OffreDTO> offreDTOS = offrePage.map(offre -> offreMapper.fromOffre(offre));

        return offreDTOS;
    }

    @Override
    public Offre findOffreById(Long id) throws OffreNotFoundException {
        log.info ("OffreServiceImpl findOffreById : {id}",id);
        Offre offre =  this.offreRepository.findById(id).orElse(null);
        if(offre == null){
            throw new OffreNotFoundException("offre not found");
        }else{
            return offre;
        }
    }
}
