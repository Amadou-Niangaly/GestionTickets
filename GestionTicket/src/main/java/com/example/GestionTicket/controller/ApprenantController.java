package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Apprenant;
import com.example.GestionTicket.service.ApprenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apprenants")
@Tag(name = "Apprenant",description = "Gestion des apprenants")
public class ApprenantController {
    @Autowired
    private ApprenantService apprenantService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //endpoint pour recuperer tous les apprenants
    @Operation(summary = "List Apprenant",description = "recuperer la liste des apprenants")
    @GetMapping
    public List<Apprenant> getAllApprenants() {
        return apprenantService.getAllApprenant();
    }
    //Endpoind pour recuperer un apprenant par id
    @Operation(summary = "Apprenant par ID",description = "recuperer un apprenant par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<Apprenant> getApprenantById(@PathVariable Long id) {
        Optional<Apprenant> apprenant = apprenantService.getApprenant(id);
        return apprenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //endpoint pour ajouter un nouvel apprenant
    @Operation(summary = "creer ",description = "creer un nouveau apprenant")
    @PostMapping("/create")
    public Apprenant add(@RequestBody Apprenant apprenant) {
        apprenant.setMotDePasse(passwordEncoder.encode(apprenant.getMotDePasse()));
        return apprenantService.addApprenant(apprenant);
    }
//endpoint pour mettre a jour un apprenant existant
@Operation(summary = "Modifier par ID",description = "Modifier un apprenant qui existe deja ")
@PutMapping("/{id}")
public ResponseEntity<Apprenant> updateApprenant(@PathVariable Long id, @RequestBody Apprenant apprenantDetails) {
    try {
        Apprenant updatedApprenant = apprenantService.updateApprenant(id, apprenantDetails);
        String encodedPassword = passwordEncoder.encode(apprenantDetails.getMotDePasse());
        updatedApprenant.setMotDePasse(encodedPassword);
        return ResponseEntity.ok(updatedApprenant);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}
//endpoint pour supprimer un apprenant par id
@Operation(summary = "Supprimer",description = "supprimer un apprenant qui existe deja")
    @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        apprenantService.deleteApprenant(id);
        return ResponseEntity.noContent().build();
 }

}
