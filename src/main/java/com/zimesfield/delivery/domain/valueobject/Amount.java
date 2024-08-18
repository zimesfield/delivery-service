package com.zimesfield.delivery.domain.valueobject;

import java.math.BigDecimal;

public record Amount(Currency currency, BigDecimal charge) {}
