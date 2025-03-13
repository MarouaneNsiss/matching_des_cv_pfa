package com.example.matching_des_cv_pfa;

import com.example.matching_des_cv_pfa.config.JwtTokenParams;
import com.example.matching_des_cv_pfa.entities.Competence;
import com.example.matching_des_cv_pfa.entities.Offre;
import com.example.matching_des_cv_pfa.enums.Region;
import com.example.matching_des_cv_pfa.service.CompetenceService;
import com.example.matching_des_cv_pfa.service.OffreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.matching_des_cv_pfa.config.RsaKeysConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeysConfig.class, JwtTokenParams.class})

public class MatchingDesCvPfaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchingDesCvPfaApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	//@Bean
	CommandLineRunner run(OffreService offreService , CompetenceService competenceService){
		return args -> {
			Offre offre = new Offre();
			Competence competence = new Competence();
			competence.setNom("springBoot");
			offre.setTitle("Python Developer");
			offre.setDescription("Looking for a skilled Java Developer.");
			offre.setExperiance("2+ years");
			offre.setRegions(Arrays.asList(Region.Casablanca_Settat));
			offre.setCompetences(Arrays.asList(competence));

			// Save the Offre
			Offre savedOffre = offreService.saveOffre(offre);
			System.out.println("Saved Offre ID: " + savedOffre.getId());

			// Find all Offres
			//System.out.println("List of Offres: " + offreService.findAllOffre().toArray().length);
			// Delete the Offre
			//offreService.deleteOffre(savedOffre.getId());
			//System.out.println("Deleted Offre ID: " + savedOffre.getId());
		};
	}
}
