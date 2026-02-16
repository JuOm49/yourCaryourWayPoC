package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.DTO.MessageDto;
import com.yourCarYourWay.api.DTO.UserDto;
import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.models.Ticket;
import com.yourCarYourWay.api.services.MessageService;
import com.yourCarYourWay.api.services.TicketService;
import com.yourCarYourWay.api.services.AuthenticationService;
import com.yourCarYourWay.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final TicketService ticketService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ChatSseController chatSseController;

    public MessageController(MessageService messageService, TicketService ticketService, AuthenticationService authenticationService, UserService userService, ChatSseController chatSseController) {
        this.messageService = messageService;
        this.ticketService = ticketService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.chatSseController = chatSseController;
    }

    @GetMapping("/messages/{userId}/{ticketId}")
    public ResponseEntity<List<Message>> getMessagesByClientIdAndTicketId(@PathVariable Long userId, @PathVariable Long ticketId)
    {
        UserDto userDto = userService.getUserById(userId);
        List<Message> messages = new ArrayList<>();
        if(userDto == null){
            return ResponseEntity.notFound().build();
        }

        messages = messageService.getMessagesByTicketId(ticketId);

        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages/create")
    public ResponseEntity<Void> createMessage(@RequestBody MessageDto messageDto){
        Ticket ticket = ticketService.getTicketById(messageDto.getTicketId());

        if (ticket == null) {
            return ResponseEntity.badRequest().build();
        }

        if (messageDto.getClientId() != null && messageDto.getClientId() != 0 && authenticationService.getUserById(messageDto.getClientId()) == null) {
            return ResponseEntity.badRequest().build();
        }
        if (messageDto.getOperatorId() != null && messageDto.getOperatorId() != 0 && authenticationService.getUserById(messageDto.getOperatorId()) == null) {
            return ResponseEntity.badRequest().build();
        }

        try{
            // Sauvegarder le message d'abord
            Message savedMessage = messageService.saveMessage(messageDto);

            // Diffuser le message via SSE (en mode "best effort")
            try {
                chatSseController.broadcastMessage(messageDto.getTicketId(), savedMessage);
            } catch (Exception sseException) {
                log.warn("Message sauvegardé mais erreur lors de la diffusion SSE: {}", sseException.getMessage(), sseException);
            }

            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            log.error("Erreur lors de la sauvegarde du message: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
