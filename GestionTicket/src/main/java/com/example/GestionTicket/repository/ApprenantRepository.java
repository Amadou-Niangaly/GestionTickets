package com.example.GestionTicket.repository;

import com.example.GestionTicket.entity.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {


}
