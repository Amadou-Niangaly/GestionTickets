package com.example.GestionTicket.service;

import com.example.GestionTicket.entity.Notification;
import com.example.GestionTicket.entity.Ticket;
import com.example.GestionTicket.repository.NotificationRepository;
import com.example.GestionTicket.repository.TicketRepository;
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

    // Notifier le changement d'état d'un ticket
    public void notifyTicketStateChange(Ticket ticket, String oldState, String newState) {
        Notification notification = new Notification();
        notification.setMessage("Le ticket " + ticket.getId() + " est passé de l'état " + oldState + " à l'état " + newState);
        notification.setDateEnvoi(new Date());
        notification.setApprenant(ticket.getApprenant());
        notification.setFormateur(ticket.getFormateur());
        createNotification(notification);

        // Envoyer des emails
        String apprenantEmail = ticket.getApprenant().getEmail();
        String formateurEmail = ticket.getFormateur().getEmail();
        String subject = "Changement d'état du ticket " + ticket.getId();
        String text = notification.getMessage();

        mailService.sendMail(apprenantEmail, subject, text);
        mailService.sendMail(formateurEmail, subject, text);
    }

    // Mettre à jour une notification
    public Notification updateNotification(int id, Notification notificationDetails) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setMessage(notificationDetails.getMessage());
            notification.setDateEnvoi(notificationDetails.getDateEnvoi());
            return notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found with id " + id);
        }
    }

    // Supprimer une notification
    public void deleteNotification(int id) {
        notificationRepository.deleteById(id);
    }

}
