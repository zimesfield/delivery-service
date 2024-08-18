package com.zimesfield.delivery.domain.model;

import com.zimesfield.delivery.domain.valueobject.Measurement;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DeliveryItemModel {

    @Setter
    private UUID uuid;

    @NotNull
    private String name;

    @NotNull
    private Integer mass;

    @NotNull
    private Measurement measurement;

    @NotNull
    private Integer quantity;

    private String description;
}
