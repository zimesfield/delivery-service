package com.zimesfield.delivery.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CustomerModel extends BaseModel {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    private String alternatePhoneNumber;

    private String email;

    @NotNull
    private String country = "Nigeria";

    @NotNull
    private String state;

    @NotNull
    private String area;

    @NotNull
    private String street;

    @NotNull
    private String landmark;
}
