package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.id.PaymentId;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record StudentDataUpdateReportEvent(

        @Nonnull
        PaymentId paymentId,
        @Nonnull
        Student student
) {

    public StudentDataUpdateReportEvent {
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(student, "student cannot be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PaymentId paymentId;
        private Student student;

        public Builder paymentId(PaymentId paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder student(Student student) {
            this.student = student;
            return this;
        }

        public StudentDataUpdateReportEvent build() {
            return new StudentDataUpdateReportEvent(paymentId, student);
        }
    }
}
