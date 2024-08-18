package com.zimesfield.delivery.adapter.rdbms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zimesfield.delivery.domain.valueobject.DeliveryStatus;
import com.zimesfield.delivery.domain.valueobject.DeliveryType;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "raeda_delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "delivery")
public class DeliveryEntity extends AuditedBaseEntity {

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CustomerEntity sender;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "delivery", orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "delivery" }, allowSetters = true)
    private Set<DeliveryItemEntity> items = new HashSet<>();

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ReceiverEntity receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @Column(name = "express_delivery")
    private Boolean express = false;

    @Column(name = "pickup_delivery")
    private Boolean pickup = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryEntity that)) return false;
        return (
            Objects.equals(getId(), that.getId()) &&
            Objects.equals(getSender(), that.getSender()) &&
            Objects.equals(getReceiver(), that.getReceiver()) &&
            getDeliveryType() == that.getDeliveryType() &&
            getStatus() == that.getStatus()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSender(), getReceiver(), getDeliveryType(), getStatus());
    }
}
