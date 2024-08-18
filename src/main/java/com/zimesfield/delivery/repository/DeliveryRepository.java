package com.zimesfield.delivery.repository;

import com.zimesfield.delivery.domain.model.DeliveryModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepository {
    DeliveryModel createDelivery(DeliveryModel deliveryModel);

    Optional<DeliveryModel> findByDeliveryId(UUID deliveryId);

    Page<DeliveryModel> findAllByPage(Pageable pageable);
}
