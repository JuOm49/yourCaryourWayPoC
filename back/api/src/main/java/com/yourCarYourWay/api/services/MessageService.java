package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public List<Message> getMessagesByClientId(Long clientId, Long ticketId) {
        return (List<Message>) messageRepository.findAll();
    }
}
