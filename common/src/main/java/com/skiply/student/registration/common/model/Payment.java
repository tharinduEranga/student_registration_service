package com.skiply.student.registration.common.model;

import com.skiply.student.registration.common.model.id.PaymentId;

import javax.money.MonetaryAmount;
import java.time.OffsetDateTime;
import java.util.Objects;

public record Payment(
        PaymentId id,
        MonetaryAmount amount,
        PaymentStatus status,
        OffsetDateTime createdAt
) {
    public Payment {
        Objects.requireNonNull(id);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(status);
        Objects.requireNonNull(createdAt);
    }

    public enum PaymentStatus {
        PENDING, SUCCESSFUL, FAILED
    }
}
