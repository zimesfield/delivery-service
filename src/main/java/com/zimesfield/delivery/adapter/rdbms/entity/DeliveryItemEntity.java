package com.zimesfield.delivery.adapter.rdbms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zimesfield.delivery.domain.valueobject.Measurement;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "raeda_delivery_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "delivery_item")
public class DeliveryItemEntity extends BaseEntity {

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "mass")
    private Integer mass;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement")
    private Measurement measurement;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    @JsonIgnoreProperties(value = { "receiver", "sender", "items" }, allowSetters = true)
    private DeliveryEntity delivery;
}
