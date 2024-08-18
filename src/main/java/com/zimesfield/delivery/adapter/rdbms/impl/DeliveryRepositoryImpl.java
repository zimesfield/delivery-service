package com.zimesfield.delivery.adapter.rdbms.impl;

import com.zimesfield.delivery.adapter.mapper.DeliveryMapper;
import com.zimesfield.delivery.adapter.rdbms.DeliveryJpaRepository;
import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.repository.DeliveryRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryModel createDelivery(DeliveryModel deliveryModel) {
        DeliveryEntity requestEntity = deliveryJpaRepository.save(deliveryMapper.toEntity(deliveryModel));
        return deliveryMapper.toModel(requestEntity);
    }

    @Override
    public Optional<DeliveryModel> findByDeliveryId(UUID deliveryId) {
        return deliveryJpaRepository.findByDeliveryId(deliveryId).map(deliveryMapper::toModel);
    }

    @Override
    public Page<DeliveryModel> findAllByPage(Pageable pageable) {
        return deliveryJpaRepository.findAll(pageable).map(deliveryMapper::toModel);
    }
}
