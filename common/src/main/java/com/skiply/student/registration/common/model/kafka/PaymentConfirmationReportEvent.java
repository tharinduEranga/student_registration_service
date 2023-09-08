package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.CardDetails;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.common.model.id.ReportId;
import com.skiply.student.registration.common.model.id.StudentId;
import jakarta.annotation.Nonnull;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public record PaymentConfirmationReportEvent(

        @Nonnull
        BigDecimal amount,

        @Nonnull
        String currency,

        @Nonnull
        StudentId studentRegistrationId,

        @Nonnull
        PaymentId paymentId,

        @Nonnull
        OffsetDateTime datetime,

        @Nonnull
        CardDetails cardDetails
) {

    public PaymentConfirmationReportEvent {
        Objects.requireNonNull(amount, "amount cannot be null");
        Objects.requireNonNull(currency, "currency cannot be null");
        Objects.requireNonNull(studentRegistrationId, "studentRegistrationId cannot be null");
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(datetime, "datetime cannot be null");
        Objects.requireNonNull(cardDetails, "cardDetails cannot be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ReportId id;
        private BigDecimal amount;
        private String currency;
        private StudentId studentRegistrationId;
        private PaymentId paymentId;
        private OffsetDateTime datetime;
        private CardDetails cardDetails;

        public Builder id(ReportId id) {
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

        public Builder studentRegistrationId(StudentId studentRegistrationId) {
            this.studentRegistrationId = studentRegistrationId;
            return this;
        }

        public Builder paymentId(PaymentId paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder datetime(OffsetDateTime datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder cardDetails(CardDetails cardDetails) {
            this.cardDetails = cardDetails;
            return this;
        }

        public PaymentConfirmationReportEvent build() {
            return new PaymentConfirmationReportEvent(amount, currency, studentRegistrationId, paymentId, datetime, cardDetails);
        }
    }
}
