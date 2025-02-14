package com.alikgizatulin.userapp.client.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakAdminClientProperties {
    private String url;
    private String realm;
    private String id;
    private String secret;
}
