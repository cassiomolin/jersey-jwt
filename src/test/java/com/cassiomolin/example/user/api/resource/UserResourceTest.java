package com.cassiomolin.example.user.api.resource;

import com.cassiomolin.example.ArquillianTest;
import com.cassiomolin.example.security.domain.Authority;
import com.cassiomolin.example.user.api.model.QueryUserResult;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

import static io.undertow.servlet.Servlets.listener;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UserResourceTest extends ArquillianTest {

    @Test
    public void getUsersAsAnonymous() {

        Response response = client.target(uri).path("api").path("users").request().get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getUsersAsAsUser() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForUser());

        Response response = client.target(uri).path("api").path("users").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getUsersAsAdmin() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForAdmin());

        Response response = client.target(uri).path("api").path("users").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<QueryUserResult> queryDetailsList = response.readEntity(new GenericType<List<QueryUserResult>>() {});
        assertNotNull(queryDetailsList);
        assertThat(queryDetailsList, hasSize(3));
    }

    @Test
    public void getUserAsAnonymous() {

        Long userId = 1L;

        Response response = client.target(uri).path("api").path("users").path(userId.toString()).request().get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getUserAsUser() {

        Long userId = 1L;

        String authorizationHeader = composeAuthorizationHeader(getTokenForUser());

        Response response = client.target(uri).path("api").path("users").path(userId.toString()).request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    @Test
    public void getUserAsAdmin() {

        Long userId = 1L;

        String authorizationHeader = composeAuthorizationHeader(getTokenForAdmin());

        Response response = client.target(uri).path("api").path("users").path(userId.toString()).request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        QueryUserResult result = response.readEntity(QueryUserResult.class);
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void getAuthenticatedUserAsAnonymous() {

        Response response = client.target(uri).path("api").path("users").path("me").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        QueryUserResult user = response.readEntity(QueryUserResult.class);
        assertNull(user.getId());
        assertEquals("anonymous", user.getUsername());
        assertThat(user.getAuthorities(), is(empty()));
    }

    @Test
    public void getAuthenticatedUserAsUser() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForUser());

        Response response = client.target(uri).path("api").path("users").path("me").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        QueryUserResult user = response.readEntity(QueryUserResult.class);
        assertNotNull(user.getId());
        assertEquals("user", user.getUsername());
        assertThat(user.getAuthorities(), containsInAnyOrder(Authority.USER));
    }

    @Test
    public void getAuthenticatedUserAsAdmin() {

        String authorizationHeader = composeAuthorizationHeader(getTokenForAdmin());

        Response response = client.target(uri).path("api").path("users").path("me").request()
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        QueryUserResult user = response.readEntity(QueryUserResult.class);
        assertNotNull(user.getId());
        assertEquals("admin", user.getUsername());
        assertThat(user.getAuthorities(), containsInAnyOrder(Authority.USER, Authority.ADMIN));
    }
}