package com.cassiomolin.example.security.api.resource;

import com.cassiomolin.example.ArquillianTest;
import com.cassiomolin.example.security.api.model.AuthenticationToken;
import com.cassiomolin.example.security.api.model.UserCredentials;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.undertow.servlet.Servlets.listener;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the authentication resource class.
 *
 * @author cassiomolin
 */
@RunWith(Arquillian.class)
public class AuthenticationResourceTest extends ArquillianTest {

    @Test
    public void authenticateWithValidCredentials() {

        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("admin");
        credentials.setPassword("password");

        Response response = client.target(uri).path("api").path("auth").request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        AuthenticationToken authenticationToken = response.readEntity(AuthenticationToken.class);
        assertNotNull(authenticationToken);
        assertNotNull(authenticationToken.getToken());
    }

    @Test
    public void authenticateWithInvalidCredentials() {

        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("invalid-user");
        credentials.setPassword("wrong-password");

        Response response = client.target(uri).path("api").path("auth").request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
}