package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.CandidatDTO;
import com.example.matching_des_cv_pfa.dto.OffreCandidatureDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.OffreCandidature;
import com.example.matching_des_cv_pfa.enums.CandidatureStatus;
import com.example.matching_des_cv_pfa.exceptions.BeneficiaireNotFoundException;
import com.example.matching_des_cv_pfa.exceptions.OffreNotFoundException;
import com.example.matching_des_cv_pfa.mappers.OffreCandidatureMapper;
import com.example.matching_des_cv_pfa.repository.BeneficiaireRepository;
import com.example.matching_des_cv_pfa.repository.OffreCandidatureRepository;
import com.example.matching_des_cv_pfa.repository.OffreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OffreCandidatureServiceImpl implements OffreCandidatureService {
    private final BeneficiaireRepository beneficiaireRepository;
    private final OffreRepository offreRepository;
    private final OffreCandidatureRepository offreCandidatureRepository;
    private final OffreCandidatureMapper offreCandidatureMapper;

    @Override
    public OffreCandidatureDTO applyTOffre(Long beneficiareId, Long OffreId) throws BeneficiaireNotFoundException, OffreNotFoundException {
        log.info("Applying offre candidature to beneficiaire {}", OffreId);
        Beneficiaire beneficiaire = beneficiaireRepository.findById(beneficiareId)
                .orElseThrow(()->new BeneficiaireNotFoundException("Beneficiaire not found"));
        Offre offre = offreRepository.findById(OffreId)
                .orElseThrow( ()->new OffreNotFoundException("Offre not found"));
        //test if the bene already aplied or not
        if (offreCandidatureRepository.existsByBeneficiaireAndOffre(beneficiaire, offre)) {
            throw new RuntimeException("You have already applied to this offer");
        }
        OffreCandidature offreCandidature = new OffreCandidature();
        offreCandidature.setBeneficiaire(beneficiaire);
        offreCandidature.setOffre(offre);
        offreCandidature.setCondidatureStatus(CandidatureStatus.PENDING);
        offreCandidature.setDateCandidature(new Date());
        offreCandidature.setDateUpdated(new Date());
        OffreCandidatureDTO offreCandidatureDTO =  this.offreCandidatureMapper.fromOffreCandidature(offreCandidatureRepository.save(offreCandidature));
        log.info("{}", offreCandidatureDTO.getCandidatureStatus());
        return offreCandidatureDTO;

    }

    @Override
    public OffreCandidatureDTO updateCandidatureStatus(Long candidatureId, CandidatureStatus newStatus) {
        OffreCandidature offreCandidature = new OffreCandidature();
        if(candidatureId != null) {
             offreCandidature = this.offreCandidatureRepository.findById(candidatureId)
                    .orElseThrow(() ->new RuntimeException("Candidature not found"));
        }
        if(newStatus != null) {
            offreCandidature.setCondidatureStatus(newStatus);
        }
        offreCandidature.setDateUpdated(new Date());
         return this.offreCandidatureMapper.fromOffreCandidature(this.offreCandidatureRepository.save(offreCandidature));

    }

    @Override
    public Page<OffreCandidatureDTO> getCandidaturesByBeneficiaire(Long beneficiaireId, int page, int size) throws BeneficiaireNotFoundException {
        Beneficiaire beneficiaire = this.beneficiaireRepository.findById(beneficiaireId)
                .orElseThrow(() -> new BeneficiaireNotFoundException("Beneficiaire not found"));

        Sort sort = Sort.by(Sort.Direction.DESC, "dateUpdated");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OffreCandidature> candidaturesPage = this.offreCandidatureRepository.findAllByBeneficiaire(beneficiaire, pageable);

        return candidaturesPage.map(offreCandidatureMapper::fromOffreCandidature);
    }


    @Override
    public Page<CandidatDTO> getCandidatsByOffre(Long recruteurId, int page, int size) throws OffreNotFoundException {
        Offre offre = this.offreRepository.findById(recruteurId)
                .orElseThrow(()->new OffreNotFoundException("offre not found exception"));

        Sort sort =  Sort.by(Sort.Direction.DESC, "dateUpdated");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OffreCandidature> offreCandidatures = this.offreCandidatureRepository.findAllByOffre(offre,pageable);
        return offreCandidatures.map(offreCandidatureMapper::toCandidatDTO);
    }
    @Override
    public void deleteCandidature(Long candidatureId){
        OffreCandidature offreCandidature = this.offreCandidatureRepository.findById(candidatureId)
                .orElseThrow(()->new RuntimeException("candidature not found"));
        this.offreCandidatureRepository.delete(offreCandidature);
    }
}
