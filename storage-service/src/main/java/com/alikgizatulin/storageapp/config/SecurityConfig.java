package com.alikgizatulin.storageapp.config;

import com.alikgizatulin.commonlibrary.security.KeycloakJwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/storage/**").authenticated()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServe ->
                        oAuth2ResourceServe.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))
                )
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
