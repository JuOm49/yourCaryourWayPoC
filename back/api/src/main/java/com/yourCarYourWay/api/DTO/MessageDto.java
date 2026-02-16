package com.yourCarYourWay.api.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long ticketId;
    private Long clientId;
    private Long operatorId;
    private Long reservationId;
    private String title;
    private String messageText;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private String attachment;
}
