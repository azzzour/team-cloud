package com.alikgizatulin.userapp.config;

import com.alikgizatulin.userapp.client.props.KeycloakAdminClientProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakAdminClientProperties.class)
public class ClientBeans {
    @Bean
    public Keycloak keycloakClient(KeycloakAdminClientProperties properties) {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getUrl())
                .realm(properties.getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(properties.getId())
                .clientSecret(properties.getSecret())
                .build();

    }
}
