package com.zimesfield.delivery.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.apidoc.customizer.JHipsterOpenApiCustomizer;

//@SecurityScheme(
//    name = "openid",
//    type = SecuritySchemeType.OPENIDCONNECT,
//    flows = @OAuthFlows(
//        authorizationCode = @OAuthFlow(
//            authorizationUrl = "http://localhost:9080/realms/jhipster/protocol/openid-connect/auth",
//            tokenUrl = "http://localhost:9080/realms/jhipster/protocol/openid-connect/token"
////                        scopes = {
////
////                                @AuthorizationScope(
////                                        name = "read",
////                                        description = "Read access"
////                                ),
////                                @AuthorizationScope(
////                                        name = "write",
////                                        description = "Write access"
////                                )
////                        }
//        )
//    )
//)

//@OpenAPIDefinition(
//    info = @Info(title = "Sample API", version = "1.0.0"),
//    security = @SecurityRequirement(name = "keycloak")
//)
//@SecurityScheme(
//    name = "oauth2",
//    type = SecuritySchemeType.OAUTH2,
//    flows = @OAuthFlows(
//        authorizationCode = @OAuthFlow(
//            authorizationUrl = "http://localhost:9080/realms/jhipster/protocol/openid-connect/auth",
//            tokenUrl = "http://localhost:9080/realms/jhipster/protocol/openid-connect/token",
//            scopes = {
//                @OAuthScope(name = "openid", description = "OpenID Connect scope"),
//                @OAuthScope(name = "profile", description = "Profile scope"),
//                @OAuthScope(name = "email", description = "Email scope"),
//                @OAuthScope(name = "jhipster", description = "Service scope for the API")
//            }
//        )
//    )
//
//)
@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    public static final String API_FIRST_PACKAGE = "com.zimesfield.delivery.web.api";

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(
        JHipsterOpenApiCustomizer jhipsterOpenApiCustomizer,
        JHipsterProperties jHipsterProperties
    ) {
        JHipsterProperties.ApiDocs properties = jHipsterProperties.getApiDocs();
        return GroupedOpenApi.builder()
            .group("openapi")
            .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch(properties.getDefaultIncludePattern())
            .build();
    }
}
