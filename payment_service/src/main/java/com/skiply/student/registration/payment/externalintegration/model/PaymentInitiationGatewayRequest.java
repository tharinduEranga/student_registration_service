package com.skiply.student.registration.payment.externalintegration.model;


import javax.money.MonetaryAmount;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * This will be the request used in the payment gateway's payment initiation
 */
public record PaymentInitiationGatewayRequest(
        MonetaryAmount amount,
        String reference, //(Payment id from the system and this will be returned to the webhook)
        String description,
        Map<String, String> metaData,
        OffsetDateTime dateTime //(Of the time of initiation in this system)
) {
    public PaymentInitiationGatewayRequest {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(reference);
        Objects.requireNonNull(dateTime);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MonetaryAmount amount;
        private String reference;
        private String description;
        private Map<String, String> metaData;
        private OffsetDateTime dateTime;

        public Builder amount(MonetaryAmount amount) {
            this.amount = amount;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder metaData(Map<String, String> metaData) {
            this.metaData = metaData;
            return this;
        }

        public Builder dateTime(OffsetDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public PaymentInitiationGatewayRequest build() {
            Objects.requireNonNull(amount);
            Objects.requireNonNull(reference);
            Objects.requireNonNull(dateTime);
            return new PaymentInitiationGatewayRequest(amount, reference, description, metaData, dateTime);
        }
    }
}
