package com.example.matching_des_cv_pfa.handlers;

import com.example.matching_des_cv_pfa.exceptions.EmailAlreadyUsedException;
import com.example.matching_des_cv_pfa.exceptions.EmailNotActivatedException;
import com.example.matching_des_cv_pfa.exceptions.EmailNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailFoundException(EmailNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleAccountNotActivatedException(UsernameNotFoundException ex) {
        // Vérifier si le message contient "non activé"
        if (ex.getMessage().contains("Ce compte n'est pas activé")) {
            return new ResponseEntity<>("Ce compte n'est pas activé. Veuillez vérifier votre adresse email pour l'activation.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
