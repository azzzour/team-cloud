package com.alikgizatulin.userapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id", "username", "email", "name", "first_name", "last_name"})
@ToString
@Builder
@Getter
@Setter
public class UserProfileDto {

    private String id;

    private String username;

    private String email;

    private String name;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
}
