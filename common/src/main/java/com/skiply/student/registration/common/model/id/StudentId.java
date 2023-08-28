package com.skiply.student.registration.common.model.id;

import java.util.Objects;
import java.util.UUID;

public record StudentId(
        String value
) {
    public StudentId(String value) {
        Objects.requireNonNull(value, "student id required");
        this.value = UUID.fromString(value).toString(); //It will throw an IllegalArgumentException if the value is not a valid UUID
    }

    public static StudentId of(String value) {
        return new StudentId(value);
    }

    public static StudentId random() {
        return new StudentId(UUID.randomUUID().toString());
    }
}
