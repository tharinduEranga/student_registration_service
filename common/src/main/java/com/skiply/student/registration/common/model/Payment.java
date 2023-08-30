package com.skiply.student.registration.common.model;

import com.skiply.student.registration.common.model.id.PaymentId;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

public record Payment(
        @Nonnull
        PaymentId id,
        @Nonnull
        MonetaryAmount amount,
        @Nonnull
        String studentRegistrationId,
        @Nullable
        String paymentIdFromGateway,
        @Nonnull
        PaymentStatus status,
        String description,
        @Nullable
        Map<String, String> metadata,
        @Nonnull
        OffsetDateTime initiatedAt,
        @Nullable
        OffsetDateTime confirmedAt
) {
    public Payment {
        Objects.requireNonNull(id);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(studentRegistrationId);
        Objects.requireNonNull(status);
        Objects.requireNonNull(initiatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PaymentId id;
        private MonetaryAmount amount;
        private String studentRegistrationId;
        private String paymentIdFromGateway;
        private PaymentStatus status;
        private String description;
        private Map<String, String> metadata;
        private OffsetDateTime initiatedAt;
        private OffsetDateTime confirmedAt;

        public Builder id(PaymentId id) {
            this.id = id;
            return this;
        }

        public Builder amount(MonetaryAmount amount) {
            this.amount = amount;
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

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
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

        public Payment build() {
            return new Payment(id, amount, studentRegistrationId, paymentIdFromGateway,
                    status, description, metadata, initiatedAt, confirmedAt);
        }
    }
}
