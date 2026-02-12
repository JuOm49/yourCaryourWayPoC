package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
