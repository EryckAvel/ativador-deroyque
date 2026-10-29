package br.com.deroyque;

import br.com.deroyque.dto.request.ActivationRequestRequest;
import br.com.deroyque.dto.response.ActivationRequestResponse;
import br.com.deroyque.mapper.ActivationRequestMapper;
import br.com.deroyque.model.ActivationRequest;
import br.com.deroyque.model.enums.StatusActivtionEnum;
import br.com.deroyque.repository.ActivationRequestRepository;
import br.com.deroyque.service.ActivationRequestService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivationRequestServiceTest {

    @Mock
    private ActivationRequestRepository activationRequestRepository;
    @Mock
    private ActivationRequestMapper activationRequestMapper;

    @InjectMocks
    private ActivationRequestService service;

    private ActivationRequestRequest request;

    @BeforeEach
    void setUp(){
        request = new ActivationRequestRequest(
                "EXT-12345",
                "CUST-10001",
                "INTERNET-500"
        );
    }

    @Test
    void deveGerarRegistroDeAtivacaoComSucesso(){
        UUID uuid = UUID.randomUUID();
        ActivationRequest activationRequest = new ActivationRequest("EXT-12345", "CUST-10001", "INTERNET-500", Instant.now(), Instant.now());
        when(activationRequestMapper.toEntity(request)).thenReturn(activationRequest);
        when(activationRequestRepository.saveAndFlush(activationRequest)).thenReturn(activationRequest);
        when(activationRequestRepository.findByExternalId(activationRequest.getExternalId())).thenReturn(Optional.empty());
        activationRequest.setId(uuid);
        when(activationRequestMapper.toResponse(activationRequest)).thenReturn(new ActivationRequestResponse(uuid, "EXT-12345", "CUST-10001", "INTERNET-500", StatusActivtionEnum.PENDING, Instant.now(), Instant.now()));
        ActivationRequestResponse response = service.registerActivation(request);
        assertEquals(uuid, response.id());
        assertEquals(StatusActivtionEnum.PENDING, response.status());
        verify(activationRequestRepository).findByExternalId(activationRequest.getExternalId());
        verify(activationRequestRepository).saveAndFlush(activationRequest);
    }

    @Test
    void deveLancarUmaExceptionAoCadastrarUmExternalIdJaExistente(){
        ActivationRequest activationRequest = new ActivationRequest("EXT-12345", "CUST-10001", "INTERNET-500", Instant.now(), Instant.now());
        when(activationRequestRepository.findByExternalId(activationRequest.getExternalId())).thenReturn(Optional.of(activationRequest));
        assertThrows(EntityExistsException.class, () -> service.registerActivation(request));
        verify(activationRequestRepository, never()).saveAndFlush(activationRequest);
    }

    @Test
    void deveRetornar400QuandoJaExistirUmaAtivacaoComExternalId(){
        UUID uuid = UUID.randomUUID();
        ActivationRequest request = new ActivationRequest(uuid, "EXT-12345", "CUST-10001", "INTERNET-500", StatusActivtionEnum.PENDING, Instant.now(), Instant.now());
        when(activationRequestRepository.findById(uuid)).thenReturn(Optional.of(request));
        when(activationRequestMapper.toResponse(request)).thenReturn(new ActivationRequestResponse(uuid, "EXT-12345", "CUST-10001", "INTERNET-500", StatusActivtionEnum.PENDING, Instant.now(), Instant.now()));
        ActivationRequestResponse response = service.getApplicationById(uuid);
        assertEquals(uuid, response.id());
        assertEquals(StatusActivtionEnum.PENDING, response.status());
        verify(activationRequestRepository).findById(uuid);
    }

    @Test
    void deveRetornar400QuandoBuscarUmIdQueNaoExiste(){
        UUID uuid = UUID.randomUUID();
        ActivationRequest request = new ActivationRequest(uuid, "EXT-12345", "CUST-10001", "INTERNET-500", StatusActivtionEnum.PENDING, Instant.now(), Instant.now());
        when(activationRequestRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getApplicationById(uuid));
        verify(activationRequestRepository).findById(uuid);
    }



}
