package com.example.GestionTicket.service;

import com.example.GestionTicket.entity.Baseconnaissance;
import com.example.GestionTicket.repository.BaseconnaissanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseconnaissanceService {
    @Autowired
    private BaseconnaissanceRepository baseconnaissanceRepository;
    //recuperer tous les base de connaissance
    public List<Baseconnaissance> getAllBaseconnaissance(){
        return baseconnaissanceRepository.findAll();
    }
    //recuperer une Baseconnaissance par son id
    public Optional<Baseconnaissance> getBaseconnaissanceById(int id){
        return baseconnaissanceRepository.findById(id);
    }
    //creer une baseconnaissance
    public Baseconnaissance createBaseconnaissance(Baseconnaissance b){
        return baseconnaissanceRepository.save(b);
    }
    //mettre a jour une base connaissance
    public Baseconnaissance updateBaseconnaissance(int id, Baseconnaissance baseconnaissanceDetails) {
        Optional<Baseconnaissance> optionalBaseconnaissance = baseconnaissanceRepository.findById(id);
        if (optionalBaseconnaissance.isPresent()) {
            Baseconnaissance baseconnaissance = optionalBaseconnaissance.get();
            baseconnaissance.setQuestion(baseconnaissanceDetails.getQuestion());
            baseconnaissance.setCategori(baseconnaissanceDetails.getCategori());
            baseconnaissance.setReponse(baseconnaissanceDetails.getReponse());
            baseconnaissance.setFormateur(baseconnaissanceDetails.getFormateur());
            return baseconnaissanceRepository.save(baseconnaissance);
        } else {
            throw new RuntimeException("Baseconnaissance not found with id " + id);
        }
    }
    //supprimer une Baseconnaissance
    public void deleteBaseconnaissance(int id) {
        baseconnaissanceRepository.deleteById(id);
    }
}
