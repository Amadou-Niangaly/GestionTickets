package com.example.GestionTicket.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Formateur extends Utilisateur {


    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Apprenant> apprenants;

    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Baseconnaissance> baseConnaissances;

    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Autres propriétés et méthodes si nécessaires
}
