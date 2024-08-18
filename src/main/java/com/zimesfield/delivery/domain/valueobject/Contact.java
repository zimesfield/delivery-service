package com.zimesfield.delivery.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Value;

public record Contact(String phoneNumber, String alternatePhoneNumber, String email) {}
