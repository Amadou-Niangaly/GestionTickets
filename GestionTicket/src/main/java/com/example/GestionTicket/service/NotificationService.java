package com.example.GestionTicket.service;

import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.entity.Notification;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.exceptions.NotificationNotFoundException;
import com.example.GestionTicket.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {


    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailService mailService;


    // Recupérer tous les notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Recupérer une notification par son id
    public Optional<Notification> getNotificationById(int id) {
        return notificationRepository.findById(id);
    }

    // Créer une notification
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    // Notifier lors du changement d'état d'un ticket
    public void notifyTicketStateChange(Ticket ticket, EtatTicket oldState, EtatTicket newState) {
        if (newState == EtatTicket.OUVERT && oldState != EtatTicket.OUVERT) {
            notifyFormateurTicketEnvoye(ticket);
        } else if (newState == EtatTicket.EN_COURS && oldState != EtatTicket.EN_COURS) {
            notifyApprenantTicketEnCours(ticket);
        } else if (newState == EtatTicket.RESOLU && oldState != EtatTicket.RESOLU) {
            notifyTicketResolu(ticket);
        }
    }

    // Notifier l'apprenant que le ticket a été ouvert
    private void notifyApprenantTicketOuvert(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Votre ticket " + ticket.getId() + " a été ouvert par le formateur " + ticket.getFormateur().getNom() + " " + ticket.getFormateur().getPrenom());
        notification.setDateEnvoi(LocalDate.now());
        notification.setApprenant(ticket.getApprenant());
        createNotification(notification);

        // Envoyer un email à l'apprenant
        String apprenantEmail = ticket.getApprenant().getEmail();
        String subject = "Ouverture de votre ticket " + ticket.getId();
        String text = notification.getMessage();
        mailService.sendMail(apprenantEmail, subject, text);
    }

    // Notifier l'apprenant que le ticket est en cours de traitement
    private void notifyApprenantTicketEnCours(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Votre ticket " + ticket.getId() + " est en cours de traitement par le formateur " + ticket.getFormateur().getNom() + " " + ticket.getFormateur().getPrenom() + ".");
        notification.setDateEnvoi(LocalDate.now());
        notification.setApprenant(ticket.getApprenant());
        createNotification(notification);

        // Envoyer un email à l'apprenant
        String apprenantEmail = ticket.getApprenant().getEmail();
        String subject = "Ticket " + ticket.getId() + " en cours de traitement";
        String text = notification.getMessage();
        mailService.sendMail(apprenantEmail, subject, text);
    }

    // Notifier le formateur que le ticket a été envoyé par l'apprenant
    private void notifyFormateurTicketEnvoye(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Le ticket " + ticket.getId() + " a été envoyé par l'apprenant."+ticket.getApprenant().getNom()+" "+ticket.getFormateur().getPrenom());
        notification.setDateEnvoi(LocalDate.now());
        notification.setFormateur(ticket.getFormateur());
        createNotification(notification);

        // Envoyer un email au formateur
        String formateurEmail = ticket.getFormateur().getEmail();
        String subject = "Envoi du ticket " + ticket.getId() + " par l'apprenant";
        String text = notification.getMessage();
        mailService.sendMail(formateurEmail, subject, text);
    }

    // Notifier l'apprenant et le formateur que le ticket a été résolu
    private void notifyTicketResolu(Ticket ticket) {
        Notification notificationApprenant = new Notification();
        notificationApprenant.setMessage("Votre ticket " + ticket.getId() + " a été résolu.");
        notificationApprenant.setDateEnvoi(LocalDate.now());
        notificationApprenant.setApprenant(ticket.getApprenant());
        createNotification(notificationApprenant);

        // Envoyer un email à l'apprenant
        String apprenantEmail = ticket.getApprenant().getEmail();
        String subjectApprenant = "Ticket " + ticket.getId() + " résolu";
        String textApprenant = notificationApprenant.getMessage();
        mailService.sendMail(apprenantEmail, subjectApprenant, textApprenant);

        Notification notificationFormateur = new Notification();
        notificationFormateur.setMessage("Le ticket " + ticket.getId() + " a été résolu.");
        notificationFormateur.setDateEnvoi(LocalDate.now());
        notificationFormateur.setFormateur(ticket.getFormateur());
        createNotification(notificationFormateur);

        // Envoyer un email au formateur
        String formateurEmail = ticket.getFormateur().getEmail();
        String subjectFormateur = "Ticket " + ticket.getId() + " résolu";
        String textFormateur = notificationFormateur.getMessage();
        mailService.sendMail(formateurEmail, subjectFormateur, textFormateur);
    }

    // Mettre à jour une notification
    public Notification updateNotification(int id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with id " + id));
        notification.setMessage(notificationDetails.getMessage());
        notification.setDateEnvoi(notificationDetails.getDateEnvoi());
        return notificationRepository.save(notification);
    }

    // Supprimer une notification
    public void deleteNotification(int id) {
        notificationRepository.deleteById(id);
    }

}
