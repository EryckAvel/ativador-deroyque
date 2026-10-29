package br.com.deroyque.service;

import br.com.deroyque.dto.request.ActivationRequestRequest;
import br.com.deroyque.dto.response.ActivationRequestResponse;
import br.com.deroyque.mapper.ActivationRequestMapper;
import br.com.deroyque.model.ActivationRequest;
import br.com.deroyque.model.enums.StatusActivtionEnum;
import br.com.deroyque.repository.ActivationRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivationRequestService {

    private static final Logger log = LoggerFactory.getLogger(ActivationRequestService.class);
    private final ActivationRequestMapper activationRequestMapper;
    private final ActivationRequestRepository activationRequestRepository;

    public ActivationRequestService(ActivationRequestMapper activationRequestMapper, ActivationRequestRepository activationRequestRepository) {
        this.activationRequestMapper = activationRequestMapper;
        this.activationRequestRepository = activationRequestRepository;
    }

    @Transactional
    public ActivationRequestResponse registerActivation(ActivationRequestRequest activationRequestRequest) {
        ActivationRequest activation = activationRequestMapper.toEntity(activationRequestRequest);
        activation.setStatus(StatusActivtionEnum.PENDING);
        activation = activationRequestRepository.saveAndFlush(activation);
        return activationRequestMapper.toResponse(activation);
    }

    public ActivationRequestResponse getApplicationById(UUID uuid) {
        ActivationRequest activationRequest = activationRequestRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Activation request not found"));
        return activationRequestMapper.toResponse(activationRequest);
    }

}
