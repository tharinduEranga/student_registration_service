package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.PaymentStatus;

public record PaymentConfirmationEvent(
        String studentId,
        String paymentId,
        PaymentStatus status,
        String message
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String studentId;
        private String paymentId;
        private PaymentStatus status;
        private String message;

        public Builder studentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder paymentId(String paymentId) {
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

        public PaymentConfirmationEvent build() {
            return new PaymentConfirmationEvent(studentId, paymentId, status, message);
        }
    }
}
