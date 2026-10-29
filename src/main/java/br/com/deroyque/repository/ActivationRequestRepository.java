package br.com.deroyque.repository;

import br.com.deroyque.model.ActivationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivationRequestRepository extends JpaRepository<ActivationRequest, UUID> {

    Optional<ActivationRequest> findByExternalId(String externalId);

}