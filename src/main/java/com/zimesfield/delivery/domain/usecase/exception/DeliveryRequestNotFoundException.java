package com.zimesfield.delivery.domain.usecase.exception;

import static com.zimesfield.delivery.domain.usecase.util.ErrorMessages.DELIVERY_NOT_FOUND;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliveryRequestNotFoundException extends RuntimeException {

    public DeliveryRequestNotFoundException() {
        super(DELIVERY_NOT_FOUND);
        log.error(DELIVERY_NOT_FOUND);
    }
}
