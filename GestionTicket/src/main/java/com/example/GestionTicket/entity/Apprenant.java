package com.example.GestionTicket.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Apprenant extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;
@JsonIgnore
    @OneToMany
    private List<Notification> notifications;
@JsonIgnore
    @OneToMany
    private List<Ticket> tickets;

    @ManyToOne
    private Admin admin;

    // Autres propriétés et méthodes si nécessaires
}
