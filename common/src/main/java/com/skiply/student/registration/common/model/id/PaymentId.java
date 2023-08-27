package com.skiply.student.registration.common.model.id;

import java.util.Objects;
import java.util.UUID;

public record PaymentId(
        String value
) {
    public PaymentId(String value) {
        Objects.requireNonNull(value, "payment id required");
        this.value = UUID.fromString(value).toString(); //It will throw an IllegalArgumentException if the value is not a valid UUID
    }
}
