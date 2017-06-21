package com.cassiomolin.example;

import com.cassiomolin.example.common.JerseyConfig;
import com.cassiomolin.example.security.api.model.UserCredentials;
import com.cassiomolin.example.security.api.resources.AuthenticationToken;
import io.undertow.servlet.Servlets;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.undertow.api.UndertowWebArchive;
import org.jboss.weld.environment.servlet.Listener;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.net.URI;

import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.listener;

public abstract class ArquillianTest {

    @ArquillianResource
    protected URI uri;

    protected Client client;

    @Deployment(testable = false)
    public static Archive<WebArchive> createDeployment() {

        return ShrinkWrap.create(UndertowWebArchive.class).from(
                deployment()
                        .setClassLoader(Application.class.getClassLoader())
                        .setContextPath("/")
                        .addListeners(listener(Listener.class))
                        .addServlets(
                                Servlets.servlet("jerseyServlet", ServletContainer.class)
                                        .setLoadOnStartup(1)
                                        .addInitParam("javax.ws.rs.Application", JerseyConfig.class.getName())
                                        .addMapping("/api/*"))
                        .setDeploymentName("application.war"));
    }

    @Before
    public void beforeTest() throws Exception {
        this.client = ClientBuilder.newClient();
    }

    protected String getTokenForAdmin() {

        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("admin");
        credentials.setPassword("password");

        AuthenticationToken authenticationToken = client.target(uri).path("api").path("auth").request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON), AuthenticationToken.class);
        return authenticationToken.getToken();
    }

    protected String getTokenForUser() {

        UserCredentials credentials = new UserCredentials();
        credentials.setUsername("user");
        credentials.setPassword("password");

        AuthenticationToken authenticationToken = client.target(uri).path("api").path("auth").request()
                .post(Entity.entity(credentials, MediaType.APPLICATION_JSON), AuthenticationToken.class);
        return authenticationToken.getToken();
    }

    protected String composeAuthorizationHeader(String authenticationToken) {
        return "Bearer" + " " + authenticationToken;
    }
}
