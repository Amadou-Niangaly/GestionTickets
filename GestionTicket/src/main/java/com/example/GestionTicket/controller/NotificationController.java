package com.example.GestionTicket.controller;

import com.example.GestionTicket.entity.Notification;
import com.example.GestionTicket.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping
    //endpoint pour recuperer tous les notifications
    @Operation(summary = "List",description = "La liste des notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
    //endpoin pour recuperer une notifications par id
    @Operation(summary = "Notification par ID",description = "recuperer une notifications")
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
     Optional<Notification> notification = notificationService.getNotificationById(id);
     return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //endpoint pour creer une notification
    @Operation(summary = "creer",description = "creer une notifications")
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }
    //endpoint pour mettre a jour une notification
    @Operation(summary = "Modifier",description = "Modifier une notifications")
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable int id, @RequestBody Notification notificationDetails) {
        try {
            Notification updatedNotification = notificationService.updateNotification(id, notificationDetails);
            return ResponseEntity.ok(updatedNotification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }
    //endpoint pour supprimer une noticatiions
    @Operation(summary = "Supprimer",description = "supprimer une  notifications")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

}
