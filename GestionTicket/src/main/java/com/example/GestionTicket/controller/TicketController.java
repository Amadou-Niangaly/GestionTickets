package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
//endpoint pour recuperer tous les tickets
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
//endpoint pour obtenir un tikt par id
@GetMapping("/{id}")
public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
    Optional<Ticket> ticket = ticketService.getTicketById(id);
    return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}
//endpoint pour creer un ticket
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }
//endpoint pour modifier un ticket
    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        return ticketService.updateTicket(id, ticketDetails);
    }
//endpoint pour supprimer un ticket
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
    }
}
