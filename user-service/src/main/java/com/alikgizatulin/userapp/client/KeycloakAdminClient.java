package com.alikgizatulin.userapp.client;

import com.alikgizatulin.userapp.dto.UserProfileDto;

import java.util.List;

public interface KeycloakAdminClient {
    List<UserProfileDto> getUsers();

    UserProfileDto getUserById(String id);
}
