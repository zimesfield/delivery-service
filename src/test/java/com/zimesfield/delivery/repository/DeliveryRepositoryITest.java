package com.zimesfield.delivery.repository;

import com.zimesfield.delivery.IntegrationTest;
import com.zimesfield.delivery.adapter.mapper.DeliveryRequestMapper;
import com.zimesfield.delivery.domain.model.CustomerModel;
import com.zimesfield.delivery.domain.model.DeliveryItemModel;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.model.DeliveryPriority;
import com.zimesfield.delivery.domain.valueobject.DeliveryStatus;
import com.zimesfield.delivery.domain.valueobject.DeliveryType;
import com.zimesfield.delivery.domain.valueobject.Measurement;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class DeliveryRepositoryITest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryRequestMapper deliveryRequestMapper;

    @BeforeEach
    void setup() {
        DeliveryModel deliveryModel = buildDeliveryModel();
        deliveryRepository.createDelivery(deliveryModel);
    }

    //    @Test
    //    void findByDeliveryId() {
    //    }

    @Test
    void findAllByPage() {
        //        Page<DeliveryModel> modelPage = deliveryRepository.findAllByPage(PageRequest.of(0, 10));
        //        deliveryRequestMapper.toResponse(Assertions.assertThat(modelPage), ArchPredicates.is(n))
    }

    private DeliveryModel buildDeliveryModel() {
        return DeliveryModel.builder()
            .deliveryId(UUID.randomUUID())
            .sender(buildCustomerModel("test@test.com", "0982543631"))
            .receiver(buildCustomerModel("test-receiver@test.com", "0809898723"))
            .deliveryId(UUID.randomUUID())
            .deliveryType(DeliveryType.PARCEL)
            .status(DeliveryStatus.NEW_REQUEST)
            .priority(DeliveryPriority.builder().express(true).pickup(true).build())
            .items(List.of(buildDeliveryItemModel()))
            .build();
    }

    private CustomerModel buildCustomerModel(String email, String phoneNumber) {
        return CustomerModel.builder()
            .country("Nigeria")
            .area("Eti Osa")
            .landmark("Hamony")
            .email(email)
            .phoneNumber(phoneNumber)
            .state("Lagos")
            .street("Aiyetoro Street")
            .build();
    }

    private DeliveryItemModel buildDeliveryItemModel() {
        return DeliveryItemModel.builder().mass(10).measurement(Measurement.KG).quantity(2).name("Laptops").uuid(UUID.randomUUID()).build();
    }
}
