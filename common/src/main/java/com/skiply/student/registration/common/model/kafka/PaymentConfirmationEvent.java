package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.PaymentStatus;
import com.skiply.student.registration.common.model.id.PaymentId;
import com.skiply.student.registration.common.model.id.StudentId;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;
import java.util.Objects;

public record PaymentConfirmationEvent(
        @Nonnull
        StudentId studentId,
        @Nonnull
        PaymentId paymentId,
        @Nonnull
        PaymentStatus status,
        @Nullable
        String message,
        @Nullable
        Map<String, String> additionalData
) {

    public PaymentConfirmationEvent {
        Objects.requireNonNull(studentId, "studentId cannot be null");
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(status, "status cannot be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private StudentId studentId;
        private PaymentId paymentId;
        private PaymentStatus status;
        private String message;
        private Map<String, String> additionalData;

        public Builder studentId(StudentId studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder paymentId(PaymentId paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder additionalData(Map<String, String> additionalData) {
            this.additionalData = additionalData;
            return this;
        }

        public PaymentConfirmationEvent build() {
            return new PaymentConfirmationEvent(studentId, paymentId, status, message, additionalData);
        }
    }
}
