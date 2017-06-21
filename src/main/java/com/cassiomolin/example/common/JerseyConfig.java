package com.cassiomolin.example.common;

import com.cassiomolin.example.common.api.providers.ObjectMapperProvider;
import com.cassiomolin.example.greeting.api.resources.GreetingResource;
import com.cassiomolin.example.security.api.exeptionmappers.AccessDeniedExceptionMapper;
import com.cassiomolin.example.security.api.exeptionmappers.AuthenticationExceptionMapper;
import com.cassiomolin.example.security.api.exeptionmappers.AuthenticationTokenRefreshmentExceptionMapper;
import com.cassiomolin.example.security.api.filter.AuthenticationFilter;
import com.cassiomolin.example.security.api.filter.AuthorizationFilter;
import com.cassiomolin.example.security.api.resources.AuthenticationResource;
import com.cassiomolin.example.user.api.resources.UserResource;
import com.cassiomolin.example.user.service.UserService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.enterprise.context.ApplicationScoped;
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