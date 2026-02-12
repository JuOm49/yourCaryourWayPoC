package com.yourCarYourWay.api.models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reservation_id")
    private long reservationId;

    @Column(name = "client_id")
    private long clientId;

    @Column(name = "operator_id")
    private long operatorId;

    @Column(name = "ticket_id")
    private long ticketId;

    private String title;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    private String attachment;
}
