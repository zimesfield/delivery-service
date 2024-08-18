package com.zimesfield.delivery.adapter.rdbms;

import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByDeliveryId(UUID deliveryId);
}
