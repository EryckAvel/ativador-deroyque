package br.com.deroyque.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URI;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        URI type,
        String title,
        int status,
        String detail,
        URI instance,
        String traceId,
        List<FieldErrorResponse> errors
) {
}