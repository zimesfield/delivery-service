package com.zimesfield.delivery.adapter.rdbms.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;

    public Long getId() {
        return id;
    }
}
