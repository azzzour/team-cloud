package com.alikgizatulin.userapp.controller;

import com.alikgizatulin.userapp.client.KeycloakAdminClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private static final String GET_PROFILE = "/profile";
    private final KeycloakAdminClient keycloakAdminClient;

    @GetMapping(GET_PROFILE)
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        System.out.println("test");
        return ResponseEntity.ok().body(this.keycloakAdminClient.getUserById(authentication.getName()));
    }
}
