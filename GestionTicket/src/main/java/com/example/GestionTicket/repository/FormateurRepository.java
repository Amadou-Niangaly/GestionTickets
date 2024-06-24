package com.example.GestionTicket.repository;

import com.example.GestionTicket.entity.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormateurRepository extends JpaRepository<Formateur,Long> {


}
