package com.zimesfield.delivery.adapter.rdbms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Setter
@Getter
@Entity
@Table(name = "raeda_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class CustomerEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country = "Nigeria";

    @Column(name = "state")
    private String state;

    @Column(name = "area")
    private String area;

    @Column(name = "street")
    private String street;

    @Column(name = "landmark")
    private String landmark;
}
