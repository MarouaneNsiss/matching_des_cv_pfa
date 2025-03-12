package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BenificiareRegistrationRequestDTO;
import com.example.matching_des_cv_pfa.dto.RecruteurRegistrationRequestDto;
import com.example.matching_des_cv_pfa.dto.passwordInitialisationRequestDto;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Role;
import com.example.matching_des_cv_pfa.entities.Utilisateur;
import com.example.matching_des_cv_pfa.enums.AccountStatus;
import com.example.matching_des_cv_pfa.exceptions.EmailAlreadyUsedException;
import com.example.matching_des_cv_pfa.exceptions.EmailNotFoundException;
import com.example.matching_des_cv_pfa.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import com.example.matching_des_cv_pfa.config.JwtTokenParams;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Transactional
@Slf4j
public class AuthserviceImpl implements Authservice {
    private UtilisateurRepository utilisateurRepository;
    private BenificiaireServiceImpl benificiaireService;
    private RecruteurService recruteurService;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private JwtTokenParams jwtTokenParams;
    private MailService mailService;
    @Value("${jwt.issuer}")
    private String issuer;

    public AuthserviceImpl(UtilisateurRepository utilisateurRepository,RecruteurService recruteurService, BenificiaireServiceImpl benificiaireService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, JwtTokenParams jwtTokenParams, MailService mailService) {
        this.utilisateurRepository = utilisateurRepository;
        this.benificiaireService = benificiaireService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.jwtTokenParams = jwtTokenParams;
        this.mailService = mailService;
      this.recruteurService=recruteurService;
    }


    @Override
    public Beneficiaire registerBenificiare(BenificiareRegistrationRequestDTO requestDTO,boolean activate) {
        Utilisateur utilisateur=utilisateurRepository.findByEmail(requestDTO.email());
        if (utilisateur != null) throw new EmailAlreadyUsedException("This email is already used");
        Beneficiaire beneficiaire= new Beneficiaire();
        beneficiaire.setPrenom(requestDTO.firstName());
        beneficiaire.setNom(requestDTO.lastName());
        beneficiaire.setEmail(requestDTO.email());
        beneficiaire.setAdresse(requestDTO.adresse());
        beneficiaire.setTelephone(requestDTO.telephone());
        beneficiaire.setPassword(passwordEncoder.encode(requestDTO.password()));
        beneficiaire.setGender(requestDTO.gender());
        beneficiaire.setStatus(AccountStatus.CREATED);
        beneficiaire.setStatus(activate?AccountStatus.ACTIVATED:AccountStatus.CREATED);
        beneficiaire.setRoles(Collections.singletonList(Role.Benificiare));
        Beneficiaire savedBenificiare = benificiaireService.saveBeneficiaire(beneficiaire);
        verifyEmail(savedBenificiare.getId());
        return savedBenificiare;
    }

    @Override
    public Recruteur registerRecruteur(RecruteurRegistrationRequestDto requestDTO, boolean activate) {
        Utilisateur utilisateur=utilisateurRepository.findByEmail(requestDTO.email());
        if (utilisateur != null) throw new EmailAlreadyUsedException("This email is already used");
        Recruteur recruteur= new Recruteur();
        recruteur.setPrenom(requestDTO.firstName());
        recruteur.setNom(requestDTO.lastName());
        recruteur.setEmail(requestDTO.email());
        recruteur.setAdresse(requestDTO.adresse());
        recruteur.setSite(requestDTO.site());
        recruteur.setPays(requestDTO.pays());
        recruteur.setTelephone(requestDTO.telephone());
        recruteur.setVille(requestDTO.ville());
        recruteur.setEntreprise(requestDTO.entreprise());
        recruteur.setNumber_employees(requestDTO.number_employees());
        recruteur.setJobFunction(requestDTO.jobFunction());
        recruteur.setImage(requestDTO.logo());
        recruteur.setPassword(passwordEncoder.encode(requestDTO.password()));
        recruteur.setGender(requestDTO.gender());
        recruteur.setStatus(AccountStatus.CREATED);
        recruteur.setStatus(activate?AccountStatus.ACTIVATED:AccountStatus.CREATED);
        recruteur.setRoles(Collections.singletonList(Role.Recruteur));
        Recruteur savedRecruteur = recruteurService.saveRecruteur(recruteur);
        verifyEmail(savedRecruteur.getId());
        return savedRecruteur;
    }

    @Override
    public Map<String,String> generateToken(String email, boolean generateRefreshToken){
        Utilisateur utilisateur=utilisateurRepository.findByEmail(email);
        String scope = utilisateur.getRoles()
                .stream()
                .map(Role::name)
                .collect(Collectors.joining(" "));        Map<String,String> idToken=new HashMap<>();
        Instant instant=Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(utilisateur.getId().toString())
                .issuedAt(instant)
                .expiresAt(instant.plus(generateRefreshToken?jwtTokenParams.shirtAccessTokenTimeout():jwtTokenParams.longAccessTokenTimeout(), ChronoUnit.MINUTES))
                .issuer(issuer)
                .claim("scope",scope)
                .claim("email",utilisateur.getEmail())
                .claim("firstName",utilisateur.getPrenom())
                .claim("lastName",utilisateur.getNom())
                .build();
        String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("access-token",jwtAccessToken);
        if(generateRefreshToken){
            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .subject(utilisateur.getId().toString())
                    .issuedAt(instant)
                    .expiresAt(instant.plus(jwtTokenParams.refreshTokenTimeout(), ChronoUnit.MINUTES))
                    .issuer(issuer)
                    .claim("username",utilisateur.getEmail())
                    .claim("email",utilisateur.getEmail())
                    .build();
            String jwtRefreshTokenToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtRefreshClaimsSet)).getTokenValue();
            idToken.put("refresh-token",jwtRefreshTokenToken);
        }
        return idToken;
    }

    @Override
    public Utilisateur findUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email) ;
    }

    @Override
    public void verifyEmail(Long userId) {
        Utilisateur utilisateur=utilisateurRepository.findById(userId).get();
        Instant instant=Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(userId.toString())
                .issuedAt(instant)
                .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
                .issuer(issuer)
                .claim("email",utilisateur.getEmail())
                .build();
        String activationJwtToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        String emailContent=String.format("To activate yous account click this link : http://localhost:8080/inscription/public/emailActivation?token="+activationJwtToken);
        mailService.sendEmail(utilisateur.getEmail(),"Email verification",emailContent);
    }

    @Override
    public String emailActivation(String token) {
        try {
            Jwt decode = jwtDecoder.decode(token);
            String subject = decode.getSubject();
            Utilisateur utilisateur=utilisateurRepository.findById(Long.parseLong(subject)).get();
            utilisateur.setEmailVerified(true);
            utilisateur.setStatus(AccountStatus.ACTIVATED);
            utilisateurRepository.save(utilisateur);
            return "Email verification success";
        } catch (JwtException e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void passwordInitialisation(String email){
        Utilisateur utilisateur=utilisateurRepository.findByEmail(email);
        System.out.println(email);
        if (utilisateur==null){
            throw new EmailNotFoundException("cet email n'est pas associé a auncun compte");
        }
        Random random=new Random();
        String password="";
        for (int i = 0; i <8 ; i++) {
            password+=random.nextInt(9);
        }
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateurRepository.save(utilisateur);
        mailService.sendEmail(email,"Password Initialization", "votre nouveau password est :"+password);
    }
}
