package com.skiply.student.registration.common.model.id;

import java.util.Objects;
import java.util.UUID;

public record ReportId(
        String value
) {
    public ReportId(String value) {
        Objects.requireNonNull(value, "report id required");
        this.value = UUID.fromString(value).toString(); //It will throw an IllegalArgumentException if the value is not a valid UUID
    }

    public static ReportId of(String value) {
        return new ReportId(value);
    }

    public static ReportId random() {
        return new ReportId(UUID.randomUUID().toString());
    }
}
