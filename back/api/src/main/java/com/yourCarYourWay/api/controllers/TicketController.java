package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.models.Ticket;
import com.yourCarYourWay.api.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService= ticketService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<Iterable<Ticket>> getAllTickets()
    {
        Iterable<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
}
