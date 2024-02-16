package com.edgomesdev;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Provider
public class FrontendExceptionMapper implements ExceptionMapper<ClientWebApplicationException> {
    @Override
    public Response toResponse(ClientWebApplicationException t) {
        return Response.status(t.getResponse().getStatus()).build();
    }
}
