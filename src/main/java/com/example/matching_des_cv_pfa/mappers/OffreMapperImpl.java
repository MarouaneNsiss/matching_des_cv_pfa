package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.LangueDTO;
import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.OffreLangue;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreMapperImpl implements OffreMapper {

    private final LangueMapper langueMapper;

    @Autowired
    public OffreMapperImpl(LangueMapper langueMapper) {
        this.langueMapper = langueMapper;
    }

    @Override
    public OffreDTO fromOffre(Offre offre) {
        OffreDTO offreDTO = new OffreDTO();

        // Map direct properties
        offreDTO.setId(offre.getId());
        offreDTO.setLogo(offre.getLogo());
        offreDTO.setTitle(offre.getTitle());
        offreDTO.setNiveauEtudesRequis(offre.getNiveauEtudesRequis());
        offreDTO.setNiveauExperience(offre.getNiveauExperience());
        offreDTO.setDateTime(offre.getDateTime());
        offreDTO.setLinkToDetails(offre.getLinkToDetails());
        offreDTO.setRecruteurId(offre.getRecruteur().getId());

        // Map regions and contrats
        offreDTO.setRegions(offre.getRegions());
        offreDTO.setContrats(offre.getContrats());

        // extract just the names of competences
        if (offre.getCompetences() != null) {
            List<String> competenceNames = offre.getCompetences().stream()
                    .map(Competence::getNom)
                    .toList();
            offreDTO.setCompetances(competenceNames);
        } else {
            offreDTO.setCompetances(new ArrayList<>());
        }

        if (offre.getRecruteur() != null) {
            offreDTO.setNom_entreprise(offre.getRecruteur().getEntreprise());
            offreDTO.setSite(offre.getRecruteur().getSite());
        }
        return offreDTO;
    }

    @Override
    public Offre fromOffreDTO(OffreDTO offreDTO) {
        Offre offre = new Offre();
        BeanUtils.copyProperties(offreDTO, offre);
        if (offreDTO.getCompetances() != null) {
            List<Competence> competenceList = offreDTO.getCompetances().stream().map(comp -> {
                Competence competence = new Competence();
                competence.setNom(comp);
                return competence;
            }).collect(Collectors.toList());
            offre.setCompetences(competenceList);
        } else {
            offre.setCompetences(new ArrayList<>());
        }


        return offre;
    }

    @Override
    public OffreDetailsDTO ToOffreDetailsDTO(Offre offre) {
        OffreDetailsDTO offreDetailsDTO = new OffreDetailsDTO();
        OffreDTO offreDTO = this.fromOffre(offre);
        offreDetailsDTO.setOffreDTO(offreDTO);
        offreDetailsDTO.setDescription(offre.getDescription());

        List<LangueDTO> langueDTOList = new ArrayList<>();
        if (offre.getOffreLangue() != null) {
            langueDTOList = offre.getOffreLangue().stream()
                    .map(offreLangue -> this.langueMapper.fromOffreLangue(offreLangue))
                    .collect(Collectors.toList());
        }
        offreDetailsDTO.setLangueDTOList(langueDTOList);

        if (offre.getRecruteur() != null) {
            offreDetailsDTO.setVille(offre.getRecruteur().getVille());
        }

        return offreDetailsDTO;
    }

    @Override
    public Offre fromOffreDetailsDTO(OffreDetailsDTO offreDetailsDTO) {
        Offre offre = this.fromOffreDTO(offreDetailsDTO.getOffreDTO());
        offre.setDescription(offreDetailsDTO.getDescription());

        List<OffreLangue> offreLangues = new ArrayList<>();
        if (offreDetailsDTO.getLangueDTOList() != null) {
            offreLangues = offreDetailsDTO.getLangueDTOList().stream()
                    .map(langueDTO -> this.langueMapper.fromLangueDTO(langueDTO))
                    .collect(Collectors.toList());
        }
        offre.setOffreLangue(offreLangues);

        return offre;
    }
}