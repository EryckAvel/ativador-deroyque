package br.com.deroyque.model;

import br.com.deroyque.model.enums.StatusActivtionEnum;
import jakarta.persistence.*;

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
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public ActivationRequest() {
    }

    public ActivationRequest(UUID id, String externalId, String customerId, String productCode, StatusActivtionEnum status,
                              OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.externalId = externalId;
        this.customerId = customerId;
        this.productCode = productCode;
        this.status = status;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
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