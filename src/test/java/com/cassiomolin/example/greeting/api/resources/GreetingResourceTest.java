package com.cassiomolin.example.greeting.api.resources;

import com.cassiomolin.example.ArquillianTest;
import com.cassiomolin.example.common.JerseyConfig;
import com.cassiomolin.example.security.api.model.UserCredentials;
import com.cassiomolin.example.security.api.resources.AuthenticationToken;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.undertow.api.UndertowWebArchive;
import org.jboss.weld.environment.servlet.Listener;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;

import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.listener;
import static org.junit.Assert.assertEquals;

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