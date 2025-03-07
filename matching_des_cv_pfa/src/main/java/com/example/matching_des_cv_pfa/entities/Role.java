package com.example.matching_des_cv_pfa.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    Benificiare,Recruteur,Admin;
    @Override
    public String getAuthority() {
        return "ROLE_" + name().toUpperCase();
    }

}
