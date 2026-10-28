CREATE TABLE activation_requests (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    external_id VARCHAR(100) NOT NULL,
    customer_id VARCHAR(100) NOT NULL,
    product_code VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT pk_activation_requests PRIMARY KEY (id),
    CONSTRAINT uk_activation_requests_external_id UNIQUE (external_id)
);