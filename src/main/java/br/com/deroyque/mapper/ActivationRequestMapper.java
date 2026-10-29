package br.com.deroyque.mapper;

import br.com.deroyque.dto.request.ActivationRequestRequest;
import br.com.deroyque.dto.response.ActivationRequestResponse;
import br.com.deroyque.model.ActivationRequest;
import org.springframework.stereotype.Component;

@Component
public class ActivationRequestMapper {

    public ActivationRequest toEntity(ActivationRequestRequest request) {
        ActivationRequest activationRequest = new ActivationRequest();
        activationRequest.setExternalId(request.externalId());
        activationRequest.setCustomerId(request.customerId());
        activationRequest.setProductCode(request.productCode());
        return activationRequest;
    }

    public ActivationRequestResponse toResponse(ActivationRequest activationRequest) {
        return new ActivationRequestResponse(
                activationRequest.getId(),
                activationRequest.getExternalId(),
                activationRequest.getCustomerId(),
                activationRequest.getProductCode(),
                activationRequest.getStatus(),
                activationRequest.getCreatedAt(),
                activationRequest.getUpdatedAt()
        );
    }
}