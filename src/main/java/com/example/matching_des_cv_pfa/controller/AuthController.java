package com.example.matching_des_cv_pfa.controller;

import com.example.matching_des_cv_pfa.dto.*;
import com.example.matching_des_cv_pfa.entities.Recruteur;
import com.example.matching_des_cv_pfa.enums.Gender;
import com.example.matching_des_cv_pfa.service.Authservice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Slf4j
public class AuthController {
    private Authservice authService;
    private AuthenticationManager authenticationManager;
    private JwtDecoder jwtDecoder;

    public AuthController(Authservice authService, AuthenticationManager authenticationManager, JwtDecoder jwtDecoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtDecoder = jwtDecoder;
    }


    @PostMapping(path = "inscription/beneficiaire/register")
    public BeneficiaireDTO registerBenificiaire(@RequestBody BenificiareRegistrationRequestDTO requestDTO){
        return  this.authService.registerBenificiare(requestDTO,false);
    }
    @PostMapping(path = "inscription/recruteur/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecruteurDTO registerRecruteur(
            @RequestPart(value = "recruteurDTO") RecruteurDTO recruteurDTO,
            @RequestPart("imageFile")MultipartFile imageFile) throws IOException {
        log.info("{}",recruteurDTO.getEmail());

        // Appel du service pour enregistrer le recruteur
        return this.authService.registerRecruteur(recruteurDTO,imageFile,false);
    }

    @GetMapping(path = "inscription/public/emailActivation")
    public String emailActivation(@RequestParam("token") String token) {
        return this.authService.emailActivation(token);
    }
    @PostMapping(path = "/motDePasseOublié")
    public ResponseEntity<String>  passwordInitialization(@RequestParam("email") String email) {
        this.authService.passwordInitialisation(email);
        return  ResponseEntity.ok("Veuillez verifié votre email");
    }
    @GetMapping(path = "/DATA")
    public String DATA(@RequestParam("data") String data) {
        return data ;
    }
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,String>> authentication(@RequestBody LoginRequestDTO authRequestDTO, HttpServletRequest request){
        String subject=authRequestDTO.email();
        String grantType = authRequestDTO.grantType();
        if (grantType == null)
            return new ResponseEntity<>(Map.of("errorMessage", "grantType is required"), HttpStatus.UNAUTHORIZED);
        if (grantType.equals("password")) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(subject, authRequestDTO.password())
            );
            subject=authentication.getName();
        } else if (grantType.equals("refreshToken")) {
            Jwt decodedRefreshToken = null;
            decodedRefreshToken = jwtDecoder.decode(authRequestDTO.refreshToken());
            subject = decodedRefreshToken.getClaim("email");

        } else {
            return new ResponseEntity<>(Map.of("errorMessage", String.format("GrantType %s not supported", grantType)), HttpStatus.UNAUTHORIZED);
        }
        Map<String, String> idToken = authService.generateToken(subject, authRequestDTO.withRefreshToken());
        return ResponseEntity.ok(idToken);

    }
}
