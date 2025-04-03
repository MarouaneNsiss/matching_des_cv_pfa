package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.dto.BeneficiaireDTO;
import com.example.matching_des_cv_pfa.dto.BenificiareRegistrationRequestDTO;
import com.example.matching_des_cv_pfa.dto.RecruteurDTO;
import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.entities.Role;
import com.example.matching_des_cv_pfa.entities.Utilisateur;
import com.example.matching_des_cv_pfa.enums.AccountStatus;
import com.example.matching_des_cv_pfa.exceptions.EmailAlreadyUsedException;
import com.example.matching_des_cv_pfa.exceptions.EmailNotFoundException;
import com.example.matching_des_cv_pfa.mappers.RecruteurMapper;
import com.example.matching_des_cv_pfa.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import com.example.matching_des_cv_pfa.config.JwtTokenParams;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Transactional
@Slf4j
public class AuthserviceImpl implements Authservice {
    private UtilisateurRepository utilisateurRepository;
    private BeneficiaireServiceImpl benificiaireService;
    private RecruteurService recruteurService;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private JwtTokenParams jwtTokenParams;
    private MailService mailService;
    @Value("${jwt.issuer}")
    private String issuer;
    private RecruteurMapper recruteurMapper;
    private FileStorageService fileStorageService;

    public AuthserviceImpl(UtilisateurRepository utilisateurRepository, BeneficiaireServiceImpl benificiaireService, RecruteurService recruteurService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, JwtTokenParams jwtTokenParams, MailService mailService, FileStorageService fileStorageService, RecruteurMapper recruteurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.benificiaireService = benificiaireService;
        this.recruteurService = recruteurService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.jwtTokenParams = jwtTokenParams;
        this.mailService = mailService;
        this.fileStorageService = fileStorageService;
        this.recruteurMapper = recruteurMapper;
    }


    @Override
    public BeneficiaireDTO registerBenificiare(BenificiareRegistrationRequestDTO requestDTO, boolean activate) {
        Utilisateur utilisateur=utilisateurRepository.findByEmail(requestDTO.email());
        if (utilisateur != null ){

                // If email exists but is not verified  status, allow re-registration
            if (!utilisateur.isEmailVerified()) {
                    // Delete the existing user
                utilisateurRepository.delete(utilisateur);
            } else {
                    throw new EmailAlreadyUsedException("This email is already used");
            }
        }
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
        BeneficiaireDTO savedBenificiareDTO =  benificiaireService.saveBeneficiaire(beneficiaire);
        verifyEmail(savedBenificiareDTO.getId());
        return savedBenificiareDTO;
    }

    @Override
    public RecruteurDTO registerRecruteur(RecruteurDTO recruteurDTO, MultipartFile imageFile, boolean activate) {
        Utilisateur utilisateur=utilisateurRepository.findByEmail(recruteurDTO.getEmail());
        if (utilisateur != null){
            // If email exists but is not verified  status, allow re-registration
            if (!utilisateur.isEmailVerified()) {
                // Delete the existing user
                utilisateurRepository.delete(utilisateur);
            } else {
                throw new EmailAlreadyUsedException("This email is already used");
            }
        }
        Recruteur recruteur = this.recruteurMapper.fromRecruteurDTO(recruteurDTO);
        recruteur.setPassword(passwordEncoder.encode(recruteurDTO.getPassword()));
        recruteur.setStatus(activate?AccountStatus.ACTIVATED:AccountStatus.CREATED);
        Recruteur savedRecruteur = this.recruteurService.saveRecruteur(recruteur);
        String fullName = savedRecruteur.getNom() + " " + savedRecruteur.getPrenom();
        // Handle image file
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(
                    imageFile,
                    fullName,
                    savedRecruteur.getId()
            );
            savedRecruteur.setImagePath(imagePath);
        }
        verifyEmail(savedRecruteur.getId());
        return this.recruteurMapper.fromRecruteur(savedRecruteur);
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
