package com.example.GestionTicket.service;
import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.entity.Apprenant;
import com.example.GestionTicket.entity.Formateur;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.repository.ApprenantRepository;
import com.example.GestionTicket.repository.FormateurRepository;
import com.example.GestionTicket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    // Créer un nouveau ticket
    public Ticket createTicket(Ticket ticket) {
        // Assurez-vous que l'entité Formateur est dans un état géré avant la sauvegarde
        if (ticket.getFormateur() != null && ticket.getFormateur().getId() != null) {
            Formateur existingFormateur = formateurRepository.findById(ticket.getFormateur().getId())
                    .orElseThrow(() -> new RuntimeException("Formateur not found with id: " + ticket.getFormateur().getId()));
            ticket.setFormateur(existingFormateur);
        }

        // Assurez-vous que l'entité Apprenant est dans un état géré avant la sauvegarde
        if (ticket.getApprenant() != null && ticket.getApprenant().getId() != null) {
            Apprenant existingApprenant = apprenantRepository.findById(ticket.getApprenant().getId())
                    .orElseThrow(() -> new RuntimeException("Apprenant not found with id: " + ticket.getApprenant().getId()));
            ticket.setApprenant(existingApprenant);
        }

        // Maintenant, sauvegardez le ticket
        Ticket createdTicket = ticketRepository.save(ticket);

        // Notifier la création du ticket
        notificationService.notifyTicketStateChange(createdTicket, null, EtatTicket.OUVERT);

        return createdTicket;
    }

    // Mettre à jour un ticket existant
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

    // Traiter un ticket et le marquer comme résolu
    public Ticket traiterTicket(int ticketId) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);

        if (!optionalTicket.isPresent()) {
            throw new RuntimeException("Ticket not found with id " + ticketId);
        }

        Ticket ticket = optionalTicket.get();
        EtatTicket oldState = ticket.getEtat();
        ticket.setEtat(EtatTicket.RESOLU);
        ticket.setDateResolution(LocalDate.now());
        Ticket updatedTicket = ticketRepository.save(ticket);

        // Notifier si l'état du ticket a changé
        notificationService.notifyTicketStateChange(updatedTicket, oldState, EtatTicket.RESOLU);

        return updatedTicket;
    }

    // Ouvrir un ticket par un formateur et le mettre à jour à EN_COURS
    public Ticket ouvrirTicketParFormateur(int ticketId, Long formateurId) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        Optional<Formateur> optionalFormateur = formateurRepository.findById(formateurId);

        if (!optionalTicket.isPresent()) {
            throw new RuntimeException("Ticket not found with id " + ticketId);
        }

        if (!optionalFormateur.isPresent()) {
            throw new RuntimeException("Formateur not found with id " + formateurId);
        }

        Ticket ticket = optionalTicket.get();
        Formateur formateur = optionalFormateur.get();
        EtatTicket oldState = ticket.getEtat();

        ticket.setEtat(EtatTicket.EN_COURS);
        ticket.setFormateur(formateur);
        Ticket updatedTicket = ticketRepository.save(ticket);

        // Notifier si l'état du ticket a changé
        notificationService.notifyTicketStateChange(updatedTicket, oldState, EtatTicket.EN_COURS);

        return updatedTicket;
    }

    // Supprimer un ticket
    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }

    // Récupérer tous les tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Récupérer un ticket par son id
    public Optional<Ticket> getTicketById(int id) {
        return ticketRepository.findById(id);
    }
}
