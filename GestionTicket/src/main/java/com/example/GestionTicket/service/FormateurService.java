package com.example.GestionTicket.service;

import com.example.GestionTicket.entity.Formateur;
import com.example.GestionTicket.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormateurService {
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
//recuperer tous les formatteurs
    public List<Formateur> getAllFormateurs(){
        return formateurRepository.findAll();
    }
    //recuperer un formateur par son id
    public Optional<Formateur> getFormateurById(Long id){
        return formateurRepository.findById(id);
    }
    //creer un nouveau formatteur
    public Formateur createFormateur(Formateur formateur){
        formateur.setMotDePasse(passwordEncoder.encode(formateur.getMotDePasse()));
        return formateurRepository.save(formateur);
    }
    //mettre à jour un formateur existant
    public Formateur updateFormateur(Long id, Formateur formateurDetails) {
        Optional<Formateur> optionalFormateur = formateurRepository.findById(id);
        if (optionalFormateur.isPresent()) {
            Formateur formateur = optionalFormateur.get();
            formateur.setNom(formateurDetails.getNom());
            formateur.setPrenom(formateurDetails.getPrenom());
            formateur.setEmail(formateurDetails.getEmail());
            formateur.setMotDePasse(formateurDetails.getMotDePasse());
            formateur.setBaseConnaissances(formateurDetails.getBaseConnaissances());
            formateur.setNotifications(formateurDetails.getNotifications());
            formateur.setTickets(formateurDetails.getTickets());
            return formateurRepository.save(formateur);
        } else {
            throw new RuntimeException("Formateur not found with id " + id);
        }
    }
    //supprimer un formateur
    public void deleteFormateurById(Long id) {
        formateurRepository.deleteById(id);
    }
}
