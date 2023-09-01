package com.skiply.student.registration.payment.repository.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment")
public class PaymentDataRecord {
    @Id
    private String id;
    private BigDecimal amount;
    private String currency;
    private String studentRegistrationId;
    private String paymentIdFromGateway;
    private String status;
    private String description;
    private String metadata;
    private OffsetDateTime initiatedAt;
    private OffsetDateTime confirmedAt;

    public PaymentDataRecord() {
        // Default constructor
    }

    public PaymentDataRecord(String id,
                             BigDecimal amount,
                             String currency,
                             String studentRegistrationId,
                             String paymentIdFromGateway,
                             String status,
                             String description,
                             String metadata,
                             OffsetDateTime initiatedAt,
                             OffsetDateTime confirmedAt
    ) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.studentRegistrationId = studentRegistrationId;
        this.paymentIdFromGateway = paymentIdFromGateway;
        this.status = status;
        this.description = description;
        this.metadata = metadata;
        this.initiatedAt = initiatedAt;
        this.confirmedAt = confirmedAt;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStudentRegistrationId() {
        return studentRegistrationId;
    }

    public String getPaymentIdFromGateway() {
        return paymentIdFromGateway;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public OffsetDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public OffsetDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setInitiatedAt(OffsetDateTime initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public void setConfirmedAt(OffsetDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public static class Builder {
        private String id;
        private BigDecimal amount;
        private String currency;
        private String studentRegistrationId;
        private String paymentIdFromGateway;
        private String status;
        private String description;
        private String metadata;
        private OffsetDateTime initiatedAt;
        private OffsetDateTime confirmedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder studentRegistrationId(String studentRegistrationId) {
            this.studentRegistrationId = studentRegistrationId;
            return this;
        }

        public Builder paymentIdFromGateway(String paymentIdFromGateway) {
            this.paymentIdFromGateway = paymentIdFromGateway;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder initiatedAt(OffsetDateTime initiatedAt) {
            this.initiatedAt = initiatedAt;
            return this;
        }

        public Builder confirmedAt(OffsetDateTime confirmedAt) {
            this.confirmedAt = confirmedAt;
            return this;
        }

        public PaymentDataRecord build() {
            return new PaymentDataRecord(id, amount, currency, studentRegistrationId,
                    paymentIdFromGateway, status, description, metadata, initiatedAt, confirmedAt);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
