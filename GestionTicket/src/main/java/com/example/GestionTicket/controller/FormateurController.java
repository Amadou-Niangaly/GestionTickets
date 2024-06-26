package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Formateur;
import com.example.GestionTicket.service.FormateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/formateurs")
@Tag(name = "Formateur",description ="Gestion des formateurs")
public class FormateurController {
    @Autowired
    private FormateurService formateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // endpoint recuperper tous les formateurs
    @Operation(summary = "List",description = "la liste des formateurs")
    @GetMapping
    public List<Formateur> getAllFormateurs() {
        return formateurService.getAllFormateurs();
    }
    //endpoint pour recuperer un formateur par id
    @Operation(summary = "Formateur par ID",description = "Recuperer un formateur par son ID ")
    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getFormateurById(@PathVariable Long id) {
        Optional<Formateur> formateur = formateurService.getFormateurById(id);
        return formateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Endpoint pour créer un formateur
    @Operation(summary = "creer",description = "creer un formateurs")
    @PostMapping("/create")
    public ResponseEntity<Formateur> createFormateur(@RequestBody Formateur formateur) {
        Formateur createdFormateur = formateurService.createFormateur(formateur);
        formateur.setMotDePasse(passwordEncoder.encode(formateur.getMotDePasse()));
        return new ResponseEntity<>(createdFormateur, HttpStatus.CREATED);
    }

    // Endpoint pour modifier un formateur
    @Operation(summary = "Modifier",description = "Modifier un formateur formateurs")
    @PutMapping("/{id}")
    public ResponseEntity<Formateur> updateFormateur(@PathVariable Long id, @RequestBody Formateur formateurDetails) {
        try {
            Formateur updatedFormateur = formateurService.updateFormateur(id, formateurDetails);
            String encodedPassword = passwordEncoder.encode(formateurDetails.getMotDePasse());
            updatedFormateur.setMotDePasse(encodedPassword);
            return new ResponseEntity<>(updatedFormateur, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer un formateur
    @Operation(summary = "supprimer",description = "supprimer un  formateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable Long id) {
        formateurService.deleteFormateurById(id);
        return ResponseEntity.noContent().build();
    }



}
