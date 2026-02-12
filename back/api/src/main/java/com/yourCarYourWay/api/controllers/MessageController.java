package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.services.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<Message[]>getMessagesByClientId(@PathVariable Long clientId, @PathVariable Long ticketId)
    {
         return messageService.getMessagesByClientId(clientId, ticketId);
    }
}
