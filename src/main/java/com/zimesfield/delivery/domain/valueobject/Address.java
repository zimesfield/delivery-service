package com.zimesfield.delivery.domain.valueobject;

import lombok.Value;

@Value
public class Address {

    String country = "Nigeria";

    String state;

    String area;

    String street;

    String landmark;
}
