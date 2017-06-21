package com.cassiomolin.example.greeting.api.resources;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
@Path("greetings")
public class GreetingResource {

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("public")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response getPublicGreeting() {
        return Response.ok("Hello from the other side!").build();
    }

    @GET
    @Path("protected")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProtectedGreeting() {
        String username = securityContext.getUserPrincipal().getName();
        return Response.ok(String.format("Hello %s!", username)).build();
    }
}