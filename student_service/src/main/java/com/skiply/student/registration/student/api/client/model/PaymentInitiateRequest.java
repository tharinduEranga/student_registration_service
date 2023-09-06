package com.skiply.student.registration.student.api.client.model;

import java.math.BigDecimal;
import java.util.List;

public record PaymentInitiateRequest(
        BigDecimal amount,
        String currency,
        String reference,
        String studentRegistrationId,
        String description,
        List<MetadataItem> metadata
) {
    public record MetadataItem(
            String key,
            String value
    ) {
    }

    public static class Builder {
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String studentRegistrationId;
        private String description;
        private List<MetadataItem> metadata;

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder studentRegistrationId(String studentRegistrationId) {
            this.studentRegistrationId = studentRegistrationId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder metadata(List<MetadataItem> metadata) {
            this.metadata = metadata;
            return this;
        }

        public PaymentInitiateRequest build() {
            return new PaymentInitiateRequest(amount, currency, reference, studentRegistrationId, description, metadata);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
