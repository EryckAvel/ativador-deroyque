package br.com.deroyque.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivationRequestRequest(

        @NotBlank(message = "O identificador externo é obrigatório.")
        @Size(max = 100, message = "O identificador externo deve ter no máximo 100 caracteres.")
        String externalId,

        @NotBlank(message = "O identificador do cliente é obrigatório.")
        @Size(max = 100, message = "O identificador do cliente deve ter no máximo 100 caracteres.")
        String customerId,

        @NotBlank(message = "O código do produto é obrigatório.")
        @Size(max = 100, message = "O código do produto deve ter no máximo 100 caracteres.")
        String productCode
) {
}