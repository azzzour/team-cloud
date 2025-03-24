package com.alikgizatulin.userapp.controller;

import com.alikgizatulin.userapp.client.KeycloakAdminClient;
import com.alikgizatulin.userapp.dto.UserProfileResponse;
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

    private static final String GET_PROFILE = "/me";
    private final KeycloakAdminClient keycloakAdminClient;

    @GetMapping(GET_PROFILE)
    public ResponseEntity<UserProfileResponse> getMe(Authentication authentication) {
        String userId = authentication.getName();
        UserProfileResponse profile = UserProfileResponse
                .fromUserModel(this.keycloakAdminClient.getById(userId));
        return ResponseEntity.ok().body(profile);
    }
}
