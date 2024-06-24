package com.example.GestionTicket.service;

import com.example.GestionTicket.entity.Apprenant;
import com.example.GestionTicket.repository.ApprenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprenantService {
    @Autowired
    private ApprenantRepository apprenantRepository;
    public List<Apprenant> getAllApprenant() {
        return apprenantRepository.findAll();
    }
    public Optional<Apprenant> getApprenant(Long id) {
        return apprenantRepository.findById(id);
    }
    //ajouter un apprenant
    public Apprenant addApprenant(Apprenant apprenant) {
        return apprenantRepository.save(apprenant);
    }
    //modifier un apprenant
    public Apprenant updateApprenant(Long id, Apprenant apprenantDetails) {
        Optional<Apprenant> optionalApprenant = apprenantRepository.findById(id);
        if (optionalApprenant.isPresent()) {
            Apprenant apprenant = optionalApprenant.get();
            apprenant.setNom(apprenantDetails.getNom());
            apprenant.setPrenom(apprenantDetails.getPrenom());
            apprenant.setEmail(apprenantDetails.getEmail());
            apprenant.setMotDePasse(apprenantDetails.getMotDePasse());
            apprenant.setFormateur(apprenantDetails.getFormateur());
            apprenant.setNotifications(apprenantDetails.getNotifications());
            apprenant.setRole(apprenantDetails.getRole());
            apprenant.setTickets(apprenantDetails.getTickets());
            return apprenantRepository.save(apprenant);
        } else {
            throw new RuntimeException("Apprenant not found with id " + id);
        }

    }
    //suprimer un apprenant
    public void deleteApprenant(Long id) {
        apprenantRepository.deleteById(id);
    }
}
