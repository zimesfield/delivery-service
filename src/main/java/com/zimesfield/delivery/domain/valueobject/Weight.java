package com.zimesfield.delivery.domain.valueobject;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class Weight {

    Integer mass;
    Measurement measurement;
}
