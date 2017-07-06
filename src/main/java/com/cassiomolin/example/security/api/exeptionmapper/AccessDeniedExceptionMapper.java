package com.cassiomolin.example.security.api.exeptionmapper;

import com.cassiomolin.example.common.api.model.ApiErrorDetails;
import com.cassiomolin.example.security.exception.AccessDeniedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for {@link AccessDeniedException}s.
 *
 * @author cassiomolin
 */
@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AccessDeniedException exception) {

        Status status = Status.FORBIDDEN;

        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage("You don't have enough permissions to perform this action.");
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }
}