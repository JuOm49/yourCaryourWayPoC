package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesByClientIdAndTicketId(Long clientId, Long ticketId) {
        return messageRepository.findByClientIdAndTicketId(clientId, ticketId);
    }
}
