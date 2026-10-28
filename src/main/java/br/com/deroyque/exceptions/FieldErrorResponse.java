package br.com.deroyque.exceptions;

public record FieldErrorResponse(String field, String message) {
}