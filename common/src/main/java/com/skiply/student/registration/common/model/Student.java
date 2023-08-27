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
}
