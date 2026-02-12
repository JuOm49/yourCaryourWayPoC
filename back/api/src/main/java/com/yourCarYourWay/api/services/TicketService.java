package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.models.Ticket;
import com.yourCarYourWay.api.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Iterable<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

}
