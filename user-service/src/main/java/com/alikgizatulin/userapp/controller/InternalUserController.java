package com.alikgizatulin.userapp.controller;

import com.alikgizatulin.commonlibrary.dto.UserSummaryDto;
import com.alikgizatulin.userapp.client.KeycloakAdminClient;
import com.alikgizatulin.userapp.factory.UserSummaryDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/users")
public class InternalUserController {

    private final KeycloakAdminClient keycloakAdminClient;
    private final UserSummaryDtoFactory userSummaryDtoFactory;


    @PostMapping("/by-ids")
    public ResponseEntity<List<UserSummaryDto>> getUsersByIds(@RequestBody List<String> ids) {
        List<UserSummaryDto> users = this.keycloakAdminClient.getByIds(ids).stream()
                .map(userSummaryDtoFactory::makeUserSummaryDto).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserSummaryDto> getUser(@PathVariable String userId,
                                                  Authentication authentication) {
        return ResponseEntity.ok(userSummaryDtoFactory
                .makeUserSummaryDto(this.keycloakAdminClient.getById(userId)));
    }
}
