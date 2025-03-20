package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Utilisateur;
import com.example.matching_des_cv_pfa.enums.AccountStatus;
import com.example.matching_des_cv_pfa.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.Optional;
@Service
@Transactional
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepository utilisateurRepository;
    private JwtDecoder jwtDecoder;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, JwtDecoder jwtDecoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Optional<Utilisateur> findUserByProfile(String token) {
        try {
            Jwt decode = jwtDecoder.decode(token);
            String subject = decode.getSubject();
            return utilisateurRepository.findById(Long.parseLong(subject));
        } catch (JwtException | NumberFormatException e) {
            e.printStackTrace();
            return Optional.empty(); // Meilleure gestion des erreurs
        }
    }

}
