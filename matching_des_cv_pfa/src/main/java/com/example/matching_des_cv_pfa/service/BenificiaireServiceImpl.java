package com.example.matching_des_cv_pfa.service;

import com.example.matching_des_cv_pfa.entities.Beneficiaire;
import com.example.matching_des_cv_pfa.repository.BeneficiaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BenificiaireServiceImpl implements BenificiaireService{
    private BeneficiaireRepository beneficiaireRepository;

    public BenificiaireServiceImpl(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    @Override
    public Beneficiaire saveBeneficiaire(Beneficiaire beneficiaire) {
        return beneficiaireRepository.save(beneficiaire);
    }

    @Override
    public List<Beneficiaire> getAllBeneficiaires() {
        return beneficiaireRepository.findAll();
    }

    @Override
    public Optional<Beneficiaire> getBeneficiaireByEmail(String email) {
        return Optional.ofNullable(beneficiaireRepository.findByEmail(email));
    }
}
