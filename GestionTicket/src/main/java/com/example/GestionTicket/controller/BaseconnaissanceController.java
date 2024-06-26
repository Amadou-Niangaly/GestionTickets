package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Baseconnaissance;
import com.example.GestionTicket.service.BaseconnaissanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/baseconnaissances")
@Tag(name = "Base des connaissances",description = "Gestion des base de connaissances ")
public class BaseconnaissanceController {
    @Autowired
    private BaseconnaissanceService baseconnaissanceService;
//endpoint pour recuperer tous les bases de connaissances
    @Operation(summary = "List",description = "la liste des base de connaissance")
    @GetMapping
    public List<Baseconnaissance> getBaseconnaissances() {
        return baseconnaissanceService.getAllBaseconnaissance();
    }
    //endpoint pour recuperer une base de connaissance par id
    @Operation(summary = "Base de connaissance par ID",description = "recuperer une  base de connaissance par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<Baseconnaissance> getBaseconnaissance(@PathVariable int id) {
        Optional<Baseconnaissance> baseconnaissance = baseconnaissanceService.getBaseconnaissanceById(id);
        return baseconnaissance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    //endpoint pour creer une base de connaissance
    @Operation(summary = "creer",description = " creer une base de connaissance")
    @PostMapping
    public ResponseEntity<Baseconnaissance> createBaseconnaissance(@RequestBody Baseconnaissance baseconnaissance) {
        Baseconnaissance createdBaseconnaissance = baseconnaissanceService.createBaseconnaissance(baseconnaissance);
        return new ResponseEntity<>(createdBaseconnaissance, HttpStatus.CREATED);
    }
    //endpoint pour mettre a jur une base de connaissance
    @Operation(summary = "Modifier par ID",description = " Modifier une base de connaissance")
    @PutMapping("/{id}")
    public ResponseEntity<Baseconnaissance> updateBaseconnaissance(@PathVariable int id, @RequestBody Baseconnaissance baseconnaissanceDetails) {
        try {
            Baseconnaissance updatedBaseconnaissance = baseconnaissanceService.updateBaseconnaissance(id, baseconnaissanceDetails);
            return ResponseEntity.ok(updatedBaseconnaissance);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //endpoint pour supprimer une base de connaissance
    @Operation(summary = "Supprimer",description = " supprimer  une base de connaissance")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseconnaissance(@PathVariable int id) {
        baseconnaissanceService.deleteBaseconnaissance(id);
        return ResponseEntity.noContent().build();
    }

}
