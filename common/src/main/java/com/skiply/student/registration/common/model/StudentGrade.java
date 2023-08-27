package com.skiply.student.registration.common.model;

import java.util.Objects;

public record StudentGrade(
        Integer grade
) {
    public StudentGrade {
        Objects.requireNonNull(grade);
    }
}
