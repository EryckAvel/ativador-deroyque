package br.com.deroyque.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        return build(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                "Um ou mais campos não passaram na validação.",
                request,
                errors
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex, HttpServletRequest request) {
        return build(
                HttpStatus.BAD_REQUEST,
                "Dados Invalidos",
                ex.getMessage(),
                request,
                null
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex, HttpServletRequest request) {
        return build(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado em {}", request.getRequestURI(), ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request,
                null
        );
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String title, String detail,
                                                  HttpServletRequest request, List<FieldErrorResponse> errors) {
        ErrorResponse problem = new ErrorResponse(
                URI.create(request.getRequestURL().toString()),
                title,
                status.value(),
                detail,
                URI.create(request.getRequestURI()),
                generateTraceId(),
                errors
        );

        return ResponseEntity.status(status).body(problem);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}