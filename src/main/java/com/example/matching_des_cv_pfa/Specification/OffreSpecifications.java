package com.example.matching_des_cv_pfa.Specification;


import com.example.matching_des_cv_pfa.dto.OffreFilterDTO;
import com.example.matching_des_cv_pfa.entities.*;
import com.example.matching_des_cv_pfa.enums.Contrat;
import com.example.matching_des_cv_pfa.enums.Region;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

//this help to creat a dynamic query based on user input like find offres by adding mutlitple where condition
public class OffreSpecifications {
    public static Specification<Offre> withFilters(OffreFilterDTO filter) {
        return Specification
                .where(competencesContain(filter.getCompetences()))
                .and(regionsIn(filter.getRegions()))
                .and(contratsIn(filter.getContrats()))
                .and(experienceLevelEquals(filter.getNiveauExperience()))
                .and(educationLevelEquals(filter.getNiveauEtudesRequis()))
                .and(languagesIn(filter.getLangues()))
                .and(keywordSearch(filter.getKeyword()))
                .and(dateRange(filter.getFromDate(), filter.getToDate()))
                .and(CompanyEquals(filter.getCompany()));
    }

    private static Specification<Offre> competencesContain(List<String> competences) {
        if (competences == null || competences.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Offre, Competence> competenceJoin = root.join("competences");
            return competenceJoin.get("nom").in(competences);
        };
    }
    private static Specification<Offre> regionsIn(List<Region> regions) {
        if (regions == null || regions.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            // Use the "member of" expression for collection memberships
            return regions.stream()
                    .map(region -> cb.isMember(region, root.get("regions")))
                    .reduce((predicate1, predicate2) -> cb.or(predicate1, predicate2))
                    .orElse(null);
        };
    }
    private static Specification<Offre> contratsIn(List<Contrat> contrats) {
        if (contrats == null || contrats.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            // Use the "member of" expression for collection memberships
            return contrats.stream()
                    .map(contrat -> cb.isMember(contrat, root.get("contrats")))
                    .reduce((predicate1, predicate2) -> cb.or(predicate1, predicate2))
                    .orElse(null);
        };
    }

    private static Specification<Offre> experienceLevelEquals(String niveau) {
        if (niveau == null || niveau.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("niveauExperience"), niveau);
    }

    private static Specification<Offre> educationLevelEquals(String niveau) {
        if (niveau == null || niveau.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> cb.equal(root.get("niveauEtudesRequis"), niveau);
    }

    private static Specification<Offre> languagesIn(List<String> langues) {
        if (langues == null || langues.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Offre, OffreLangue> offreLangueJoin = root.join("offreLangue");
            Join<OffreLangue, Langue> langueJoin = offreLangueJoin.join("langue");
            return langueJoin.get("langueName").in(langues);
        };
    }

    private static Specification<Offre> keywordSearch(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    private static Specification<Offre> dateRange(Date fromDate, Date toDate) {
        if (fromDate == null && toDate == null) {
            return null;
        }

        if (fromDate != null && toDate == null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dateTime"), fromDate);
        }

        if (fromDate == null) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dateTime"), toDate);
        }

        return (root, query, cb) -> cb.between(root.get("dateTime"), fromDate, toDate);
    }

    private static Specification<Offre> CompanyEquals(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            Join<Offre, Recruteur> recruteurJoin = root.join("recruteur");
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.like(cb.lower(recruteurJoin.get("entreprise")), pattern);
        };
    }

}
