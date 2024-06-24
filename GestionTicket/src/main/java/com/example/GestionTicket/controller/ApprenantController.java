package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Apprenant;
import com.example.GestionTicket.service.ApprenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apprenants")
public class ApprenantController {
    @Autowired
    private ApprenantService apprenantService;

    //endpoint pour recuperer tous les apprenants
    @GetMapping
    public List<Apprenant> getAllApprenants() {
        return apprenantService.getAllApprenant();
    }
    //Endpoind pour recuperer un apprenant par id
    @GetMapping("/{id}")
    public ResponseEntity<Apprenant> getApprenantById(@PathVariable Long id) {
        Optional<Apprenant> apprenant = apprenantService.getApprenant(id);
        return apprenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //endpoint pour ajouter un nouvel apprenant

    @PostMapping()
    public Apprenant add(@RequestBody Apprenant apprenant) {
        return apprenantService.addApprenant(apprenant);
    }
//endpoint pour mettre a jour un apprenant existant
@PutMapping("/{id}")
public ResponseEntity<Apprenant> updateApprenant(@PathVariable Long id, @RequestBody Apprenant apprenantDetails) {
    try {
        Apprenant updatedApprenant = apprenantService.updateApprenant(id, apprenantDetails);
        return ResponseEntity.ok(updatedApprenant);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}
//endpoint pour supprimer un apprenant par id
    @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        apprenantService.deleteApprenant(id);
        return ResponseEntity.noContent().build();
 }

}
