package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Utilisateur;
import com.example.matching_des_cv_pfa.enums.AccountStatus;
import com.example.matching_des_cv_pfa.exceptions.EmailNotActivatedException;
import com.example.matching_des_cv_pfa.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.matching_des_cv_pfa.entities.Role;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyUserDetails implements UserDetailsService {

    private Authservice authService;

    public MyUserDetails(Authservice authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException,EmailNotActivatedException {
        Utilisateur appUser = authService.findUserByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }
        if (!appUser.getStatus().equals(AccountStatus.ACTIVATED))
            throw new UsernameNotFoundException("Ce compte n'est pas activé");
        Collection<GrantedAuthority> authorities = appUser.getRoles()
                .stream()
                .map(Role::getAuthority)  // Pas besoin de SimpleGrantedAuthority !
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(email, appUser.getPassword(), authorities);
    }
}
