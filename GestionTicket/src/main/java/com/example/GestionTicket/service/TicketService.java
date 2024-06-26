package com.example.GestionTicket.service;

import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private NotificationService notificationService;
    //recuperer tous les ticket
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    //recuperer un ticket par son id
    public Optional<Ticket> getTicketById(int id) {
        return ticketRepository.findById(id);
    }
    //creer un nouveau ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    // Modifier un ticket déjà existant
    public Ticket updateTicket(int id, Ticket ticketDetails) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();

            // Enregistrer l'état actuel avant la mise à jour
            EtatTicket oldState = ticket.getEtat();

            // Mettre à jour les attributs du ticket
            ticket.setDescription(ticketDetails.getDescription());
            ticket.setUrgence(ticketDetails.getUrgence());
            ticket.setEtat(ticketDetails.getEtat());
            ticket.setDateCreation(ticketDetails.getDateCreation());
            ticket.setDateResolution(ticketDetails.getDateResolution());
            ticket.setApprenant(ticketDetails.getApprenant());
            ticket.setFormateur(ticketDetails.getFormateur());

            Ticket updatedTicket = ticketRepository.save(ticket);

            // Notifier si l'état du ticket a changé
            EtatTicket newState = updatedTicket.getEtat();
            if (oldState != newState) {
                notificationService.notifyTicketStateChange(updatedTicket, oldState, newState);
            }

            return updatedTicket;
        } else {
            throw new RuntimeException("Ticket not found with id " + id);
        }
    }
    //supprimer un ticket
    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }
}
