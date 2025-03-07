package com.example.matching_des_cv_pfa;

import com.example.matching_des_cv_pfa.config.JwtTokenParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.matching_des_cv_pfa.config.RsaKeysConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
