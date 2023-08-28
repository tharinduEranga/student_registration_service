package com.skiply.student.registration.common.model;

import java.util.Objects;

public record StudentGrade(
        Integer value
) {
    public StudentGrade {
        Objects.requireNonNull(value);
    }

    public static StudentGrade of(Integer grade) {
        return new StudentGrade(grade);
    }

    public static StudentGrade fromString(String grade) {
        return new StudentGrade(Integer.parseInt(grade));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
