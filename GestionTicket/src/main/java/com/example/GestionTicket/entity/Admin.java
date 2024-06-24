package com.example.GestionTicket.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends Utilisateur {
@JsonIgnore
    @OneToMany
    private List<Formateur> formateurs;

     @JsonIgnore
    @OneToMany
    private List<Apprenant> apprenants;

    // Autres propriétés et méthodes si nécessaires
}
