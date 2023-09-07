package com.skiply.student.registration.payment.externalintegration.model;


import java.util.Objects;

/**
 * This will be the response used in the payment gateway's payment initiation
 */
public record PaymentInitiationGatewayResponse(
        String id
) {
    public PaymentInitiationGatewayResponse {
        Objects.requireNonNull(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public PaymentInitiationGatewayResponse build() {
            Objects.requireNonNull(id);
            return new PaymentInitiationGatewayResponse(id);
        }
    }
}
