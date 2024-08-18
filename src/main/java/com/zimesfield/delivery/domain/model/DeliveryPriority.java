package com.zimesfield.delivery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Builder
public class DeliveryPriority {

    private Boolean express = false;

    private Boolean pickup = true;
}
