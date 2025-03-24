package com.alikgizatulin.userapp.client;

import com.alikgizatulin.userapp.model.UserModel;

import java.util.List;

public interface KeycloakAdminClient {
    List<UserModel> getAll();

    List<UserModel> getByIds(List<String> ids);

    UserModel getById(String id);
}
