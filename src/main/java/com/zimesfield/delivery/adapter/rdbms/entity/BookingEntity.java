package com.zimesfield.delivery.adapter.rdbms.entity;

import com.zimesfield.delivery.domain.valueobject.Amount;
import jakarta.persistence.*;
import java.util.UUID;

public class BookingEntity {

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private CustomerEntity sender;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private ReceiverEntity receiver;

    private Amount amount;
}
