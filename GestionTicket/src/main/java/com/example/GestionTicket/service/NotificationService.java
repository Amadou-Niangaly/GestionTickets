package com.example.GestionTicket.service;

import com.example.GestionTicket.Enum.EtatTicket;
import com.example.GestionTicket.entity.Notification;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.repository.NotificationRepository;
import com.example.GestionTicket.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public void notifyTicketStateChange(Ticket ticket, EtatTicket oldState, EtatTicket newState) {
        if (newState == EtatTicket.OUVERT && oldState != EtatTicket.OUVERT) {
            notifyApprenantTicketOuvert(ticket);
            notifyFormateurTicketEnvoye(ticket);
        } else if (newState == EtatTicket.EN_COURS && oldState != EtatTicket.EN_COURS) {
            notifyFormateurTicketEnvoye(ticket);
        } else if (newState == EtatTicket.ENVOYE && oldState != EtatTicket.ENVOYE) {
            notifyFormateurTicketEnvoyeParApprenant(ticket);
        }
    }
    // Notifier l'apprenant que le ticket a été ouvert
    private void notifyApprenantTicketOuvert(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Votre ticket " + ticket.getId() + " a été ouvert par le formateur  "+ticket.getFormateur().getNom()+" "+ticket.getFormateur().getPrenom());
        notification.setDateEnvoi(new Date());
        notification.setApprenant(ticket.getApprenant());
        createNotification(notification);

        // Envoyer un email à l'apprenant
        String apprenantEmail = ticket.getApprenant().getEmail();
        String subject = "Ouverture de votre ticket " + ticket.getId();
        String text = notification.getMessage();

        mailService.sendMail(apprenantEmail, subject, text);
    }

    private void notifyFormateurTicketEnvoyeParApprenant(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Le ticket " + ticket.getId() + " vous a été envoyé par l'apprenant.");
        notification.setDateEnvoi(new Date());
        notification.setFormateur(ticket.getFormateur());
        createNotification(notification);

        // Envoyer un email au formateur
        String formateurEmail = ticket.getFormateur().getEmail();
        String subject = "Envoi du ticket " + ticket.getId() + " par l'apprenant";
        String text = notification.getMessage();

        mailService.sendMail(formateurEmail, subject, text);

    }
    // Notifier le formateur que le ticket a été envoyé (mis en cours)
    private void notifyFormateurTicketEnvoye(Ticket ticket) {
        Notification notification = new Notification();
        notification.setMessage("Le ticket " + ticket.getId() + " a été envoyer par l'apprenant "+ticket.getApprenant().getNom()+" "+ticket.getApprenant().getPrenom());
        notification.setDateEnvoi(new Date());
        notification.setFormateur(ticket.getFormateur());
        createNotification(notification);

        // Envoyer un email au formateur
        String formateurEmail = ticket.getFormateur().getEmail();
        String subject = "Envoi du ticket " + ticket.getId();
        String text = notification.getMessage();

        mailService.sendMail(formateurEmail, subject, text);
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
