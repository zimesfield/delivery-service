package com.zimesfield.delivery.adapter.mapper; //package com.zimesfield.booking.adapter.service.mapper;

import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DeliveryEntity} and its Model {@link DeliveryModel}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    //    DeliveryModel toModel(DeliveryEntity deliveryRequestEntity);

    //    DeliveryResponse toResponse(DeliveryModel deliveryModel);
}
