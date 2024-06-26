package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Admin;
import com.example.GestionTicket.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
@Tag(name = "Admin", description = "Gestion des administrateurs")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
  //TOUS LES ADMIN
    @GetMapping
    @Operation(summary = "list", description = " reucperer liste des admins")
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }
    //par Id
    @Operation(summary = "Admin par ID",description = "recuperer un admin par son ID")
    @GetMapping("/{id}")

    public ResponseEntity<Admin> getAdminById(@PathVariable Long id){
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //creer
    @Operation(summary = "creer un admin",description = "creer un nouveau admin")
    @PostMapping

    public Admin createAdmin(@RequestBody Admin admin){
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
        return adminService.createAdmin(admin);
    }
    // Mettre à jour un admin par ID
    @Operation(summary = "Modifier par ID",description = "Modifier un admin par son ID")
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        Admin admin = adminService.updateAdmin(id, updatedAdmin);
        String encodedPassword = passwordEncoder.encode(updatedAdmin.getMotDePasse());
        admin.setMotDePasse(encodedPassword);
        return ResponseEntity.ok(admin);
    }

    // Supprimer un admin par ID
    @Operation(summary = "Supprimer par son ID",description = "supprimer un apprenant par son ID")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
