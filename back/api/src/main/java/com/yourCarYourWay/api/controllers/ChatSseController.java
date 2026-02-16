package com.yourCarYourWay.api.controllers;

import com.yourCarYourWay.api.DTO.MessageSseDto;
import com.yourCarYourWay.api.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatSseController {

    // Map pour stocker les connexions SSE par ticket
    private final Map<Long, Set<SseEmitter>> ticketEmitters = new ConcurrentHashMap<>();


     // Endpoint SSE pour stream des messages d'un ticket
    @GetMapping(value = "/stream/{ticketId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMessages(@PathVariable Long ticketId) {
        SseEmitter emitter = new SseEmitter(300000L); // Timeout de 5 minutes

        // Ajouter l'emitter à la liste pour ce ticket
        ticketEmitters.computeIfAbsent(ticketId, k -> ConcurrentHashMap.newKeySet()).add(emitter);

        // Envoyer un message de connexion initial
        try {
            emitter.send(SseEmitter.event()
                    .name("connection")
                    .data("Connecté au chat du ticket " + ticketId));
        } catch (IOException e) {
            removeEmitter(ticketId, emitter);
            return emitter;
        }

        // Nettoyage automatique en cas de déconnexion
        emitter.onCompletion(() -> {
            removeEmitter(ticketId, emitter);
        });
        emitter.onTimeout(() -> {
            removeEmitter(ticketId, emitter);
        });
        emitter.onError(throwable -> {
            removeEmitter(ticketId, emitter);
        });

        return emitter;
    }


    //Vérifier si un emitter est encore utilisable
    private boolean isEmitterAlive(SseEmitter emitter) {
        try {
            // Essayer d'envoyer un ping silencieux, data à "" pour éviter les problèmes de sérialisation
            emitter.send(SseEmitter.event().name("ping").data(""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Diffuser un nouveau message à tous les clients connectés sur ce ticket
    public void broadcastMessage(Long ticketId, Message message) {
        Set<SseEmitter> emitters = ticketEmitters.get(ticketId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        MessageSseDto messageSseDto = MessageSseDto.fromMessage(message);

        List<SseEmitter> deadEmitters = new ArrayList<>();

        // Copier la liste pour éviter les modifications concurrentes
        Set<SseEmitter> emittersCopy = new HashSet<>(emitters);

        emittersCopy.forEach(emitter -> {
            try {
                // Vérifier si l'émetteur est encore utilisable
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(messageSseDto));
            } catch ( java.lang.IllegalStateException | IOException e) {
                deadEmitters.add(emitter);
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        // Supprimer les connexions mortes
        if (!deadEmitters.isEmpty()) {
            deadEmitters.forEach(deadEmitter -> removeEmitter(ticketId, deadEmitter));
        }
    }

    private void removeEmitter(Long ticketId, SseEmitter emitter) {
        try {
            Set<SseEmitter> emitters = ticketEmitters.get(ticketId);
            if (emitters != null) {
                emitters.remove(emitter);
                if (emitters.isEmpty()) {
                    ticketEmitters.remove(ticketId);
                }
            }
        } catch (Exception e) {
        }

        // fermer proprement l'émetteur
        try {
            emitter.complete();
        } catch (Exception e) {
        }
    }
}
