package com.zimesfield.delivery.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zimesfield.delivery.domain.valueobject.Amount;
import com.zimesfield.delivery.domain.valueobject.DeliveryStatus;
import com.zimesfield.delivery.domain.valueobject.DeliveryType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DeliveryModel extends BaseModel {

    @Setter
    private UUID deliveryId;

    private CustomerModel sender;

    private CustomerModel receiver;

    private DeliveryType deliveryType;

    @Setter
    private DeliveryStatus status;

    private DeliveryPriority priority;

    private List<DeliveryItemModel> items;

    private Amount estimatedAmount;

    private LocalDateTime pickupDate;

    private LocalDateTime deliveryDate;
}
