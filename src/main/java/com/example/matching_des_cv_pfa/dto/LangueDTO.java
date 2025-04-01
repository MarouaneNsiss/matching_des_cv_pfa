package com.example.matching_des_cv_pfa.dto;

import com.example.matching_des_cv_pfa.enums.LangueLevel;
import com.example.matching_des_cv_pfa.enums.LangueName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LangueDTO {
    private LangueName langueName;
    private LangueLevel langueLevel;
}
