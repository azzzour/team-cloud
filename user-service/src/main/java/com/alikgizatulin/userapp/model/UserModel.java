package com.alikgizatulin.userapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserModel {

    private String id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

}
