package com.cassiomolin.example.common.api.config;

import com.cassiomolin.example.common.api.providers.ObjectMapperProvider;
import com.cassiomolin.example.greeting.api.resource.GreetingResource;
import com.cassiomolin.example.security.api.exeptionmapper.AccessDeniedExceptionMapper;
import com.cassiomolin.example.security.api.exeptionmapper.AuthenticationExceptionMapper;
import com.cassiomolin.example.security.api.exeptionmapper.AuthenticationTokenRefreshmentExceptionMapper;
import com.cassiomolin.example.security.api.filter.AuthenticationFilter;
import com.cassiomolin.example.security.api.filter.AuthorizationFilter;
import com.cassiomolin.example.security.api.resource.AuthenticationResource;
import com.cassiomolin.example.user.api.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey configuration class.
 *
 * @author cassiomolin
 */
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(AuthenticationResource.class);
        register(GreetingResource.class);
        register(UserResource.class);

        register(AuthenticationFilter.class);
        register(AuthorizationFilter.class);

        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(AuthenticationTokenRefreshmentExceptionMapper.class);

        register(ObjectMapperProvider.class);
    }
}