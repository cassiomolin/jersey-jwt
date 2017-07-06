package com.cassiomolin.example.greeting.api.resource;

import com.cassiomolin.example.greeting.service.GreetingService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * JAX-RS resource class that provides operations for greetings.
 *
 * @author cassiomolin
 */
@RequestScoped
@Path("greetings")
public class GreetingResource {

    @Context
    private SecurityContext securityContext;

    @Inject
    private GreetingService greetingService;

    @GET
    @Path("public")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response getPublicGreeting() {
        return Response.ok(greetingService.getPublicGreeting()).build();
    }

    @GET
    @Path("protected")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProtectedGreeting() {
        String username = securityContext.getUserPrincipal().getName();
        return Response.ok(greetingService.getGreetingForUser(username)).build();
    }
}