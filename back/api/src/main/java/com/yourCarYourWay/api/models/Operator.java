package com.yourCarYourWay.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Operators")
public class Operator {

    @Id private long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String role;

    private Boolean availability;
}
