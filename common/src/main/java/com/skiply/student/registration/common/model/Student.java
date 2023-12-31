package com.skiply.student.registration.common.model;

import com.skiply.student.registration.common.model.id.StudentId;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

public record Student(
        @Nonnull
        StudentId id,
        @Nonnull
        String name,
        @Nonnull
        StudentGrade grade,
        @Nonnull
        String mobile,
        @Nonnull
        StudentStatus status,
        @Nullable
        String school
) {
    public Student {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(grade);
        Objects.requireNonNull(mobile);
        Objects.requireNonNull(status);
    }

    public static class Builder {
        private StudentId id;
        private String name;
        private StudentGrade grade;
        private String mobile;
        private StudentStatus status;
        private String school;

        public Builder id(StudentId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder grade(StudentGrade grade) {
            this.grade = grade;
            return this;
        }

        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder status(StudentStatus status) {
            this.status = status;
            return this;
        }

        public Builder school(String school) {
            this.school = school;
            return this;
        }

        public Student build() {
            return new Student(id, name, grade, mobile, status, school);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
