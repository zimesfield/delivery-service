package com.zimesfield.delivery;

import com.zimesfield.delivery.config.AsyncSyncConfiguration;
import com.zimesfield.delivery.config.EmbeddedElasticsearch;
import com.zimesfield.delivery.config.EmbeddedKafka;
import com.zimesfield.delivery.config.EmbeddedSQL;
import com.zimesfield.delivery.config.JacksonConfiguration;
import com.zimesfield.delivery.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { DeliveryApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedElasticsearch
@EmbeddedSQL
@EmbeddedKafka
public @interface IntegrationTest {
}
