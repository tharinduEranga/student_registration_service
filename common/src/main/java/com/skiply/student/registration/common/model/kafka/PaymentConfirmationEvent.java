package com.skiply.student.registration.common.model.kafka;

import com.skiply.student.registration.common.model.PaymentStatus;

public record PaymentConfirmationEvent(
        String paymentId,
        PaymentStatus status,
        String message
) {
}
