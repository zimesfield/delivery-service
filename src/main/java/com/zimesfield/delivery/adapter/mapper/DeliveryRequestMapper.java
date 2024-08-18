package com.zimesfield.delivery.adapter.mapper; //package com.zimesfield.booking.adapter.service.mapper;

import com.zimesfield.delivery.adapter.rdbms.entity.DeliveryEntity;
import com.zimesfield.delivery.domain.model.CustomerModel;
import com.zimesfield.delivery.domain.model.DeliveryItemModel;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.model.DeliveryPriority;
import com.zimesfield.delivery.model.*;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link DeliveryEntity} and its Model {@link DeliveryModel}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryRequestMapper extends EntityMapper<DeliveryModel, DeliveryRequest> {
    @Mapping(source = "bioData.firstName", target = "firstName")
    @Mapping(source = "bioData.lastName", target = "lastName")
    @Mapping(source = "contact.phoneNumber", target = "phoneNumber")
    @Mapping(source = "contact.alternatePhoneNumber", target = "alternatePhoneNumber")
    @Mapping(source = "contact.email", target = "email")
    @Mapping(source = "address.country", target = "country")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.area", target = "area")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.landmark", target = "landmark")
    CustomerModel toModel(Customer customer);

    @Mapping(target = "bioData.firstName", source = "firstName")
    @Mapping(target = "bioData.lastName", source = "lastName")
    @Mapping(target = "contact.phoneNumber", source = "phoneNumber")
    @Mapping(target = "contact.alternatePhoneNumber", source = "alternatePhoneNumber")
    @Mapping(target = "contact.email", source = "email")
    @Mapping(target = "address.country", source = "country")
    @Mapping(target = "address.state", source = "state")
    @Mapping(target = "address.area", source = "area")
    @Mapping(target = "address.street", source = "street")
    @Mapping(target = "address.landmark", source = "landmark")
    Customer toResponse(CustomerModel customer);

    DeliveryResponse toResponse(DeliveryModel deliveryModel);

    DeliveryPriority map(Priority value);
    Priority map(DeliveryPriority value);

    @Mapping(source = "weight.mass", target = "mass")
    @Mapping(source = "weight.measurement", target = "measurement")
    DeliveryItemModel map(Item item);

    @Mapping(target = "weight.mass", source = "mass")
    @Mapping(target = "weight.measurement", source = "measurement")
    Item map(DeliveryItemModel itemModel);

    List<DeliveryItemModel> mapToModel(List<Item> items);

    List<Item> mapToResponse(List<DeliveryItemModel> itemModels);
}
