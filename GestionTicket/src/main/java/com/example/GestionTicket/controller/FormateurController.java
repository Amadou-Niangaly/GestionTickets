package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Formateur;
import com.example.GestionTicket.service.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/formateurs")
public class FormateurController {
    @Autowired
    private FormateurService formateurService;
    // endpoint recuperper tous les formateurs
    @GetMapping
    public List<Formateur> getAllFormateurs() {
        return formateurService.getAllFormateurs();
    }
    //endpoint pour recuperer un formateur par id
    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getFormateurById(@PathVariable Long id) {
        Optional<Formateur> formateur = formateurService.getFormateurById(id);
        return formateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Endpoint pour créer un formateur
    @PostMapping
    public ResponseEntity<Formateur> createFormateur(@RequestBody Formateur formateur) {
        Formateur createdFormateur = formateurService.createFormateur(formateur);
        return new ResponseEntity<>(createdFormateur, HttpStatus.CREATED);
    }

    // Endpoint pour modifier un formateur
    @PutMapping("/{id}")
    public ResponseEntity<Formateur> updateFormateur(@PathVariable Long id, @RequestBody Formateur formateurDetails) {
        try {
            Formateur updatedFormateur = formateurService.updateFormateur(id, formateurDetails);
            return new ResponseEntity<>(updatedFormateur, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer un formateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        formateurService.deleteFormateurById(id);
        return ResponseEntity.noContent().build();
    }



}
