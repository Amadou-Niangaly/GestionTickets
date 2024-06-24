package com.example.GestionTicket.controller;
import com.example.GestionTicket.entity.Utilisateur;
import com.example.GestionTicket.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequestMapping("/user")
@RestController
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    //recuperer tous les utilisateur
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateur();
    }
    //creer
    @PostMapping()
    public Utilisateur addUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }
    //utilisateur par son id
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //modifier un tulisateur
public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable("id") Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur updateUtilisateur=utilisateurService.updateUtilisateur(id, utilisateur);
        return ResponseEntity.ok(updateUtilisateur);
}
//supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
