package com.yourCarYourWay.api.services;

import com.yourCarYourWay.api.DTO.MessageDto;
import com.yourCarYourWay.api.models.Message;
import com.yourCarYourWay.api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Message> getMessagesByTicketId(Long ticketId) {
        return messageRepository.findByTicketId(ticketId);
    }

    public Message saveMessage(MessageDto messageDto) {
        if (messageDto == null) {
            throw new IllegalArgumentException("MessageDto ne peut pas être null");
        }


        Message message = convertMessageDtoToMessage(messageDto);
        return messageRepository.save(message);
    }

    private Message convertMessageDtoToMessage(MessageDto messageDto) {
        Message message = new Message();

        message.setClientId(isValidId(messageDto.getClientId()) ? messageDto.getClientId() : null);
        message.setOperatorId(isValidId(messageDto.getOperatorId()) ? messageDto.getOperatorId() : null);
        message.setTicketId(messageDto.getTicketId());

        // Pour le PoC : forcer reservationId à 2
        message.setReservationId(2L);

        message.setTitle(messageDto.getTitle());
        message.setMessageText(messageDto.getMessageText());
        message.setSentAt(messageDto.getSentAt() != null ? messageDto.getSentAt() : LocalDateTime.now());
        message.setReadAt(messageDto.getReadAt());
        message.setAttachment(messageDto.getAttachment());


        return message;
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }
}
