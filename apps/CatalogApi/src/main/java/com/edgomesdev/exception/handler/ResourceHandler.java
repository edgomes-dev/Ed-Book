package com.edgomesdev.exception.handler;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.dto.ErrorResponseDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ResourceHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof NotFoundException) {
            return handleNotFoundException((NotFoundException) e);
        } else if (e instanceof IllegalArgumentException) {
            return handleIllegalArgumentException((IllegalArgumentException) e);
        } else {
            return handleGenericException(e);
        }
    }

    private Response handleNotFoundException(NotFoundException e) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(e.getMessage())
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .build();

        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }

    private Response handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message("ID inválido")
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }

    private Response handleGenericException(Exception e) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(e.getMessage())
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
