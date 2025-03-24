package com.alikgizatulin.userapp.client;

import com.alikgizatulin.userapp.client.props.KeycloakAdminClientProperties;
import com.alikgizatulin.userapp.exception.UserNotFoundException;
import com.alikgizatulin.userapp.model.UserModel;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakAdminClientDefaultImpl implements KeycloakAdminClient {

    private final Keycloak keycloakClient;

    private final KeycloakAdminClientProperties properties;

    @Override
    public List<UserModel> getAll() {
        RealmResource realmResource = this.keycloakClient.realm(properties.getRealm());
        return realmResource.users()
                .list().stream()
                .map(user -> UserModel
                        .builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()).toList();
    }

    @Override
    public List<UserModel> getByIds(List<String> ids) {
        List<UserModel> users = new ArrayList<>();
        for(String id: ids) {
            users.add(this.getById(id));
        }
        return users;
    }

    @Override
    public UserModel getById(String id) {
        RealmResource realmResource = this.keycloakClient.realm(properties.getRealm());
        try {
            var representation = realmResource.users().get(id).toRepresentation();
            realmResource.users().get(id).getUnmanagedAttributes();
            return UserModel
                    .builder()
                    .firstName(representation.getFirstName())
                    .lastName(representation.getLastName())
                    .username(representation.getUsername())
                    .id(representation.getId())
                    .email(representation.getEmail())
                    .build();
        } catch (NotFoundException ex) {
            throw new UserNotFoundException(id);
        }
    }

}
