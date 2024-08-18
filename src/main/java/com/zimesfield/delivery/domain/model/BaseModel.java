package com.zimesfield.delivery.domain.model;

import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseModel extends AuditableModel {

    private Long id;
}
