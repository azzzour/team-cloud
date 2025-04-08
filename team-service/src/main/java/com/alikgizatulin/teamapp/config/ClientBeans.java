package com.alikgizatulin.teamapp.config;

import com.alikgizatulin.teamapp.client.UserRestClient;
import com.alikgizatulin.teamapp.client.UserRestClientDefault;
import com.alikgizatulin.commonlibrary.security.OAuthClientHttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public UserRestClient userRestClient(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService

    ) {
        return new UserRestClientDefault( RestClient.builder()
                .baseUrl("http://localhost:8080")
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                oAuth2AuthorizedClientService),
                        "keycloak"
                ))
                .build()
        );
    }
}
