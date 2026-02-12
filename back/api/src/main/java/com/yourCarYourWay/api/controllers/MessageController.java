package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.services.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/{clientId}/{ticketId}")
    public ResponseEntity<List<Message>> getMessagesByClientIdAndTicketId(@PathVariable Long clientId, @PathVariable Long ticketId)
    {
        List<Message> messages = messageService.getMessagesByClientIdAndTicketId(clientId, ticketId);
        return ResponseEntity.ok(messages);
    }
}
