package com.example.GestionTicket.controller;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/traiter")
    public ResponseEntity<Ticket> traiterTicket(@PathVariable int id) {
        Ticket updatedTicket = ticketService.traiterTicket(id);
        return ResponseEntity.ok(updatedTicket);
    }

    @PutMapping("/{ticketId}/ouvrir/{formateurId}")
    public ResponseEntity<Ticket> ouvrirTicketParFormateur(@PathVariable("ticketId") int ticketId, @PathVariable("formateurId") Long formateurId) {
        Ticket updatedTicket = ticketService.ouvrirTicketParFormateur(ticketId, formateurId);
        return ResponseEntity.ok(updatedTicket);
    }
}
