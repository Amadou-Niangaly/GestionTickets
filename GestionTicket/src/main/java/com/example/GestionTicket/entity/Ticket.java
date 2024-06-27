package com.example.GestionTicket.entity;

import com.example.GestionTicket.Enum.CategoriTicket;
import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.Enum.Urgence;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    @Enumerated(EnumType.STRING)
    private Urgence urgence;
    @Enumerated(EnumType.STRING)
    private CategoriTicket categori;
    @Enumerated(EnumType.STRING)
    private EtatTicket etat;

    private LocalDate dateCreation;

    private LocalDate dateResolution;

    @ManyToOne(cascade = CascadeType.PERSIST) // Assurez-vous que le fetch type est correct
    @JoinColumn(name = "apprenant_id")

    private Apprenant apprenant;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;

}
