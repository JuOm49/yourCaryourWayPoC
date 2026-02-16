package com.yourCarYourWay.api.repositories;

import com.yourCarYourWay.api.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByClientIdAndTicketId(Long clientId, Long ticketId);

    List<Message> findByTicketId(Long ticketId);
}
