package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Baseconnaissance;
import com.example.GestionTicket.service.BaseconnaissanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/baseconnaissances")
public class BaseconnaissanceController {
    @Autowired
    private BaseconnaissanceService baseconnaissanceService;
//endpoint pour recuperer tous les bases de connaissances
    @GetMapping
    public List<Baseconnaissance> getBaseconnaissances() {
        return baseconnaissanceService.getAllBaseconnaissance();
    }
    //endpoint pour recuperer une base de connaissance par id
    @GetMapping("/{id}")
    public ResponseEntity<Baseconnaissance> getBaseconnaissance(@PathVariable int id) {
        Optional<Baseconnaissance> baseconnaissance = baseconnaissanceService.getBaseconnaissanceById(id);
        return baseconnaissance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    //endpoint pour creer une base de connaissance
    @PostMapping
    public ResponseEntity<Baseconnaissance> createBaseconnaissance(@RequestBody Baseconnaissance baseconnaissance) {
        Baseconnaissance createdBaseconnaissance = baseconnaissanceService.createBaseconnaissance(baseconnaissance);
        return new ResponseEntity<>(createdBaseconnaissance, HttpStatus.CREATED);
    }
    //endpoint pour mettre a jur une base de connaissance
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseconnaissance(@PathVariable int id) {
        baseconnaissanceService.deleteBaseconnaissance(id);
        return ResponseEntity.noContent().build();
    }

}
