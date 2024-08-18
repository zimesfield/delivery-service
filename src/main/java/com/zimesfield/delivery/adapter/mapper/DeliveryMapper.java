package com.zimesfield.delivery.adapter.mapper; //package com.zimesfield.booking.adapter.service.mapper;

import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link DeliveryEntity} and its Model {@link DeliveryModel}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryMapper extends EntityMapper<DeliveryModel, DeliveryEntity> {
    @Mapping(source = "priority.express", target = "express")
    @Mapping(source = "priority.pickup", target = "pickup")
    DeliveryEntity toEntity(DeliveryModel deliveryModel);

    @Mapping(target = "priority.express", source = "express")
    @Mapping(target = "priority.pickup", source = "pickup")
    DeliveryModel toModel(DeliveryEntity deliveryEntity);
}
