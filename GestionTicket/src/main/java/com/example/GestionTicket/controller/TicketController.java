package com.example.GestionTicket.controller;

import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.service.TicketService;
import com.example.GestionTicket.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets",description = "Gestion des tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

    // Endpoint pour récupérer tous les tickets
    @Operation(summary = "List",description = "La liste des tickets")
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // Endpoint pour obtenir un ticket par id
    @Operation(summary = "Obtenir",description = "obtenir un tickets")
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Endpoint pour créer un ticket
    @Operation(summary = "creer",description = "creer un tickets")
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        // Notifier la création du ticket
        notificationService.notifyTicketStateChange(createdTicket, null, EtatTicket.OUVERT);
        return ResponseEntity.ok(createdTicket);
    }

    // Endpoint pour modifier un ticket
    @Operation(summary = "Modifier",description = "Modifier un  ticket")
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        Ticket oldTicket = ticketService.getTicketById(id).orElse(null);
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);

        // Notifier le changement d'état du ticket
        if (oldTicket != null && !oldTicket.getEtat().equals(updatedTicket.getEtat())) {
            notificationService.notifyTicketStateChange(updatedTicket, oldTicket.getEtat(), updatedTicket.getEtat());
        }

        return ResponseEntity.ok(updatedTicket);
    }

    // Endpoint pour supprimer un ticket
    @Operation(summary = "supprimer",description = "supprimer un ticket")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
