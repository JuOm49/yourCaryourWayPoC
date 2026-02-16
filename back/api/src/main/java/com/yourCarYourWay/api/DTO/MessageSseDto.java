package com.yourCarYourWay.api.DTO;

import com.yourCarYourWay.api.models.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageSseDto {
    private Long id;
    private Long clientId;
    private Long operatorId;
    private Long ticketId;
    private Long reservationId;
    private String title;
    private String messageText;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private String attachment;

    public static MessageSseDto fromMessage(Message message) {
        MessageSseDto dto = new MessageSseDto();
        dto.setId(message.getId());
        dto.setClientId(message.getClientId());
        dto.setOperatorId(message.getOperatorId());
        dto.setTicketId(message.getTicketId());
        dto.setReservationId(message.getReservationId());
        dto.setTitle(message.getTitle());
        dto.setMessageText(message.getMessageText());
        dto.setSentAt(message.getSentAt());
        dto.setReadAt(message.getReadAt());
        dto.setAttachment(message.getAttachment());
        return dto;
    }
}


