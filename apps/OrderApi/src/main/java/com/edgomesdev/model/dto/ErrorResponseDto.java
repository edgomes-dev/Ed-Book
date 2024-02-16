package com.edgomesdev.model.dto;

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
