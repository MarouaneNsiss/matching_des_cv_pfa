package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Utilisateur;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Optional<Utilisateur> findUserByProfile(String token);

}
