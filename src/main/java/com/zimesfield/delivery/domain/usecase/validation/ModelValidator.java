package com.zimesfield.delivery.domain.usecase.validation;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import com.zimesfield.delivery.domain.model.DeliveryModel;
import jakarta.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

public class ModelValidator {

    public static Optional<Set<ConstraintViolation<DeliveryModel>>> validate(DeliveryModel model) {
        try (var factory = buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            return Optional.of(validator.validate(model));
        }
    }
}
