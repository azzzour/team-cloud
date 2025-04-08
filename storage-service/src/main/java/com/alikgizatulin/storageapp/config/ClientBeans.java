package com.alikgizatulin.storageapp.config;

import com.alikgizatulin.commonlibrary.security.OAuthClientHttpRequestInterceptor;
import com.alikgizatulin.storageapp.client.TeamServiceRestClient;
import com.alikgizatulin.storageapp.client.TeamServiceServiceRestClientDefault;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;


@Configuration
public class ClientBeans {
    @Bean
    public TeamServiceRestClient teamMemberRestClient(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {

        return new TeamServiceServiceRestClientDefault(RestClient.builder()
                .baseUrl("http://localhost:8081")
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                oAuth2AuthorizedClientService),
                        "keycloak"
                ))
                .build());
    }
}
