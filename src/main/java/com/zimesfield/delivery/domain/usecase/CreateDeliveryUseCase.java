package com.zimesfield.delivery.domain.usecase;

import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.usecase.validation.ModelValidator;
import com.zimesfield.delivery.domain.valueobject.DeliveryStatus;
import com.zimesfield.delivery.repository.DeliveryRepository;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateDeliveryUseCase extends BaseUseCase {

    private final DeliveryRepository deliveryRepository;

    public DeliveryModel apply(DeliveryModel deliveryModel) {
        enrichModel(deliveryModel);
        validate(deliveryModel);
        saveDelivery(deliveryModel);
        return deliveryModel;
    }

    private void saveDelivery(DeliveryModel deliveryModel) {
        deliveryRepository.createDelivery(deliveryModel);
    }

    private void enrichModel(DeliveryModel deliveryModel) {
        deliveryModel.setDeliveryId(UUID.randomUUID());
        deliveryModel.setStatus(DeliveryStatus.NEW_REQUEST);
        deliveryModel.getItems().forEach(f -> f.setUuid(UUID.randomUUID()));
    }

    private void validate(DeliveryModel deliveryModel) {
        ModelValidator.validate(deliveryModel)
            .filter(violations -> !violations.isEmpty())
            .ifPresent(violations -> throwIf(violations, deliveryModel.getDeliveryId().toString()));
    }

    private void throwIf(Set<ConstraintViolation<DeliveryModel>> violations, String deliveryId) {
        throw Problem.builder()
            .withTitle("Validation Error")
            .withStatus(Status.BAD_REQUEST)
            .withDetail(violations.toString())
            .with("delivery-id", deliveryId)
            .build();
    }
}
