package com.skiply.student.registration.common.model;

import com.skiply.student.registration.common.model.id.StudentId;

import java.util.Objects;

public record Student(
        StudentId id,
        String name,
        StudentGrade grade,
        String mobile,
        String school
) {
    public Student {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(grade);
        Objects.requireNonNull(mobile);
        Objects.requireNonNull(school);
    }

    public static class Builder {
        private StudentId id;
        private String name;
        private StudentGrade grade;
        private String mobile;
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

        public Builder school(String school) {
            this.school = school;
            return this;
        }

        public Student build() {
            return new Student(id, name, grade, mobile, school);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
