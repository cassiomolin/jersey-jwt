package com.cassiomolin.example.greeting.api.resource;

import com.cassiomolin.example.ArquillianTest;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static io.undertow.servlet.Servlets.listener;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the greeting resource class.
 *
 * @author cassiomolin
 */
@RunWith(Arquillian.class)
public class GreetingResourceTest extends ArquillianTest {

    @Test
    public void getPublicGreetingAsAnonymous() {

        Response response = client.target(uri).path("api").path("greetings").path("public").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getProtectedGreetingAsAnonymous() {

        Response response = client.target(uri).path("api").path("greetings").path("protected").request().get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getPublicGreetingAsAdmin() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForAdmin());

        Response response = client.target(uri).path("api").path("greetings").path("public").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getProtectedGreetingAsAdmin() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForAdmin());

        Response response = client.target(uri).path("api").path("greetings").path("protected").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}