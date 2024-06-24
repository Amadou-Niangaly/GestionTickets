package com.example.GestionTicket.repository;

import com.example.GestionTicket.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Integer> {
}
