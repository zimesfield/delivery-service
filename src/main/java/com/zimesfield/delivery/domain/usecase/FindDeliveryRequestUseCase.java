package com.zimesfield.delivery.domain.usecase;

import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.usecase.exception.DeliveryRequestNotFoundException;
import com.zimesfield.delivery.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindDeliveryRequestUseCase extends BaseUseCase {

    private final DeliveryRepository deliveryRepository;

    public DeliveryModel apply(UUID deliveryId) {
        return deliveryRepository.findByDeliveryId(deliveryId).orElseThrow(DeliveryRequestNotFoundException::new);
    }

    public Page<DeliveryModel> apply(Pageable pageable) {
        return deliveryRepository.findAllByPage(pageable);
    }
}
