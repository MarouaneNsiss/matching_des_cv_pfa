package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.LangueDTO;
import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.dto.OffreDetailsDTO;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.entities.OffreLangue;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.service.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OffreMapperImpl implements OffreMapper {

    private final LangueMapper langueMapper;
    private final FileStorageService fileStorageService;
    @Autowired
    public OffreMapperImpl(LangueMapper langueMapper, FileStorageService fileStorageService) {
        this.langueMapper = langueMapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public OffreDTO fromOffre(Offre offre) {
        OffreDTO offreDTO = new OffreDTO();

        // Map direct properties
        offreDTO.setId(offre.getId());
        offreDTO.setTitle(offre.getTitle());
        offreDTO.setNiveauEtudesRequis(offre.getNiveauEtudesRequis());
        offreDTO.setNiveauExperience(offre.getNiveauExperience());
        offreDTO.setDateTime(offre.getDateTime());
        offreDTO.setLinkToDetails(offre.getLinkToDetails());

        // Map regions and contrats
        offreDTO.setRegions(offre.getRegions());
        offreDTO.setContrats(offre.getContrats());

        // Extract just the names of competences
        if (offre.getCompetences() != null) {
            List<String> competenceNames = offre.getCompetences().stream()
                    .map(Competence::getNom)
                    .toList();
            offreDTO.setCompetances(competenceNames);
        } else {
            offreDTO.setCompetances(new ArrayList<>());
        }

        // Handle logo assignment
        if (offre.getIsScraped() || offre.getRecruteur() == null) {
            // Scraped offer: Use the URL directly
            offreDTO.setEntreprise(offre.getEntreprise());
            offreDTO.setLogoURL(offre.getLogoURL());
            offreDTO.setLogo(null);// No byte[] data for scraped offers
            offreDTO.setIsScraped(Boolean.TRUE);
        } else {
            // Manually added offer: Convert logo to byte[] if needed
            offreDTO.setRecruteurId(offre.getRecruteur().getId());
            offreDTO.setEntreprise(offre.getRecruteur().getEntreprise());
            offreDTO.setSite(offre.getRecruteur().getSite());

            if (offre.getLogo() != null) {
                try {
                    // Read logo file as byte array
                    Path logoPath = fileStorageService.getFilePath(offre.getLogo(), true);
                    byte[] logoBytes = Files.readAllBytes(logoPath);
                    offreDTO.setLogo(logoBytes);
                } catch (IOException e) {
                    log.error("Error loading logo for offer: " + offre.getId(), e);
                    offreDTO.setLogo(null);
                }
            }
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