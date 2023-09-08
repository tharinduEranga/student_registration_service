package com.skiply.student.registration.reporting.model;

import com.skiply.student.registration.common.model.CardDetails;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.common.model.id.ReportId;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import javax.money.MonetaryAmount;
import java.time.OffsetDateTime;
import java.util.Objects;

public record Report(
        @Nonnull
        ReportId id,

        @Nonnull
        MonetaryAmount amount,

        @Nonnull
        StudentId studentRegistrationId,

        @Nullable
        String studentName,

        @Nonnull
        PaymentId paymentId,

        @Nonnull
        OffsetDateTime datetime,

        @Nonnull
        CardDetails cardDetails
) {
    public Report {
        Objects.requireNonNull(id, "id cannot be null");
        Objects.requireNonNull(amount, "amount cannot be null");
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
        private MonetaryAmount amount;
        private StudentId studentRegistrationId;
        private String studentName;
        private PaymentId paymentId;
        private OffsetDateTime datetime;
        private CardDetails cardDetails;

        public Builder id(ReportId id) {
            this.id = id;
            return this;
        }

        public Builder amount(MonetaryAmount amount) {
            this.amount = amount;
            return this;
        }

        public Builder studentRegistrationId(StudentId studentRegistrationId) {
            this.studentRegistrationId = studentRegistrationId;
            return this;
        }

        public Builder studentName(String studentName) {
            this.studentName = studentName;
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

        public Report build() {
            return new Report(id, amount, studentRegistrationId, studentName, paymentId, datetime, cardDetails);
        }
    }
}
