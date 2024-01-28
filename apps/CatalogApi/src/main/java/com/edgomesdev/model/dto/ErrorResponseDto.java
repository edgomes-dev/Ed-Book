package com.edgomesdev.model.dto;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private int statusCode;
}
