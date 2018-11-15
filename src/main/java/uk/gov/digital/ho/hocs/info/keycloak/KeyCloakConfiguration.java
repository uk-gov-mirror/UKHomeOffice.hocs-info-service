package uk.gov.digital.ho.hocs.info.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class KeyCloakConfiguration {

    @Bean
    public Keycloak keycloakClient(@Value("${keycloak.server.url}") String serverUrl,
                                   @Value("${keycloak.realm}") String realm,
                                   @Value("${keycloak.username}") String username,
                                   @Value("${keycloak.password}") String password,
                                   @Value("${keycloak.client.id}") String clientId) {

        if (StringUtils.isEmpty(serverUrl)) {
            throw new BeanCreationException("Failed to create Keycloak client bean. Need non-blank value for serverUrl");
        }
        if (StringUtils.isEmpty(realm)) {
            throw new BeanCreationException("Failed to create Keycloak client bean. Need non-blank value for realm");
        }
        if (StringUtils.isEmpty(username)) {
            throw new BeanCreationException("Failed to create Keycloak client bean. Need non-blank value for username");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BeanCreationException("Failed to create Keycloak client bean. Need non-blank value for password");
        }
        if (StringUtils.isEmpty(clientId)) {
            throw new BeanCreationException("Failed to create Keycloak client bean. Need non-blank value for clientId");
        }

        return Keycloak.getInstance(
                serverUrl,realm, username,password,clientId,clientId);
    }

}