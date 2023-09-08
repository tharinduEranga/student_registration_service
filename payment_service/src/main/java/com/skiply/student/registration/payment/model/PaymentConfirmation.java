package com.skiply.student.registration.payment.model;

import com.skiply.student.registration.common.model.CardDetails;
import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.id.PaymentId;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.OffsetDateTime;
import java.util.Objects;

public record PaymentConfirmation(
        @Nonnull
        PaymentId id,
        @Nonnull
        PaymentStatus status,
        @Nonnull
        CardDetails cardDetails,
        @Nullable
        OffsetDateTime confirmedAt
) {
    public PaymentConfirmation {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        Objects.requireNonNull(cardDetails);
        Objects.requireNonNull(confirmedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PaymentId id;
        private PaymentStatus status;
        private CardDetails cardDetails;
        private OffsetDateTime confirmedAt;

        public Builder id(PaymentId id) {
            this.id = id;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder cardDetails(CardDetails cardDetails) {
            this.cardDetails = cardDetails;
            return this;
        }

        public Builder confirmedAt(OffsetDateTime confirmedAt) {
            this.confirmedAt = confirmedAt;
            return this;
        }

        public PaymentConfirmation build() {
            return new PaymentConfirmation(id, status, cardDetails, confirmedAt);
        }
    }
}
