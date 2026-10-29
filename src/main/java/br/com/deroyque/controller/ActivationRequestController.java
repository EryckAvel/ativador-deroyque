package br.com.deroyque.controller;

import br.com.deroyque.dto.request.ActivationRequestRequest;
import br.com.deroyque.dto.response.ActivationRequestResponse;
import br.com.deroyque.service.ActivationRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/requests")
public class ActivationRequestController {

    private final ActivationRequestService service;

    public ActivationRequestController(ActivationRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActivationRequestResponse> registerActivationRequest(@RequestBody @Valid ActivationRequestRequest activationRequestRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerActivation(activationRequestRequest));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ActivationRequestResponse> getActivationRequest(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getApplicationById(uuid));
    }

}
