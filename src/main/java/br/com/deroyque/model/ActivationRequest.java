package br.com.deroyque.model;

import br.com.deroyque.model.enums.StatusActivtionEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "activation_requests")
public class ActivationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "external_id")
    private String externalId;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusActivtionEnum status;
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public ActivationRequest() {
    }

    public ActivationRequest(UUID id, String externalId, String customerId, String productCode, StatusActivtionEnum status,
                             Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.externalId = externalId;
        this.customerId = customerId;
        this.productCode = productCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ActivationRequest(String externalId, String customerId, String productCode, Instant createdAt, Instant updatedAt) {
        this.externalId = externalId;
        this.customerId = customerId;
        this.productCode = productCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public StatusActivtionEnum getStatus() {
        return status;
    }

    public void setStatus(StatusActivtionEnum status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivationRequest that = (ActivationRequest) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}