package com.alikgizatulin.userapp.client;

import com.alikgizatulin.userapp.client.props.KeycloakAdminClientProperties;
import com.alikgizatulin.userapp.dto.UserProfileDto;
import com.alikgizatulin.userapp.exception.UserNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakAdminClientDefaultImpl implements KeycloakAdminClient {

    private final Keycloak keycloakClient;

    private final KeycloakAdminClientProperties properties;

    @Override
    public List<UserProfileDto> getUsers() {
        RealmResource realmResource = this.keycloakClient.realm(properties.getRealm());
        return realmResource.users()
                .list().stream()
                .map(user -> UserProfileDto
                        .builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .name(user.getFirstName() + " " + user.getLastName())
                        .email(user.getEmail())
                        .build()).toList();
    }

    @Override
    public UserProfileDto getUserById(String id) {
        RealmResource realmResource = this.keycloakClient.realm(properties.getRealm());
        try {
            var representation = realmResource.users().get(id).toRepresentation();
            realmResource.users().get(id).getUnmanagedAttributes();
            return UserProfileDto
                    .builder()
                    .name(representation.getFirstName() + " " + representation.getLastName())
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
