package com.zimesfield.delivery.web.rest;

import static org.junit.jupiter.api.Assertions.*;

import com.zimesfield.delivery.IntegrationTest;
import com.zimesfield.delivery.config.EmbeddedKafka;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@EmbeddedKafka
@ImportAutoConfiguration(TestChannelBinderConfiguration.class)
class DeliveryResourceIT {

    @BeforeEach
    void setUp() {}

    @Test
    void getDeliveries() {}

    @Test
    void getDelivery() {}

    @Test
    void request() {}
}
