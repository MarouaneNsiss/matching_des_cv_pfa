package com.example.matching_des_cv_pfa.mappers;

import com.example.matching_des_cv_pfa.dto.OffreDTO;
import com.example.matching_des_cv_pfa.entities.Offre;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreMapperImpl implements OffreMapper {

    @Override
    public OffreDTO fromOffre(Offre offre) {
        OffreDTO offreDTO = new OffreDTO();


        // Map direct properties
        offreDTO.setId(offre.getId());
        offreDTO.setLogo(offre.getLogo());
        offreDTO.setTitle(offre.getTitle());
        offreDTO.setNiveau_etudes_requis(offre.getNiveau_etudes_requis());
        offreDTO.setNiveau_experience(offre.getExperiance());
        offreDTO.setDatetime(offre.getDateTime());
        offreDTO.setLinkToDetails(offre.getLink_to_details());

        // Map regions and contrats
        offreDTO.setRegions(offre.getRegions());
        offreDTO.setContrats(offre.getContrats());

        // extract just the names of competances
        if (offre.getCompetences() != null) {
            List<String> competenceNames = offre.getCompetences().stream()
                    .map(competence -> competence.getNom())
                    .toList();
            offreDTO.setCompetances(competenceNames);
        }
        if(offre.getRecruteur() != null){
            offreDTO.setNom_entreprise(offre.getRecruteur().getEntreprise());
            offreDTO.setSite(offre.getRecruteur().getSite());
        }
        return offreDTO;
    }

}
