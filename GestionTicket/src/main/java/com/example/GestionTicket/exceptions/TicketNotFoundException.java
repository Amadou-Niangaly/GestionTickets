package com.example.GestionTicket.exceptions;

public class TicketNotFoundException extends  RuntimeException {
    public TicketNotFoundException(String message) {
        super(message);
    }
}
