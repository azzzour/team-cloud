package com.alikgizatulin.userapp.dto;

import com.alikgizatulin.userapp.model.UserModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserProfileResponse (String id, String username, String email, String name,
                                   String firstName, String lastName) {

    public static UserProfileResponse fromUserModel(UserModel user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName() + " " + user.getLastName(),
                user.getFirstName(),
                user.getLastName()
                );
    }
}
