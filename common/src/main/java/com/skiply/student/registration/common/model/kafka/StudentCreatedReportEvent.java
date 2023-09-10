package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.Student;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public record StudentCreatedReportEvent(

        @Nonnull
        Student student
) {

    public StudentCreatedReportEvent {
        Objects.requireNonNull(student, "student cannot be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Student student;

        public Builder student(Student student) {
            this.student = student;
            return this;
        }

        public StudentCreatedReportEvent build() {
            return new StudentCreatedReportEvent(student);
        }
    }
}
