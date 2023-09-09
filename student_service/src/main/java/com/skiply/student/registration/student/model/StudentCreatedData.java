package com.skiply.student.registration.student.model;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.id.PaymentId;

import java.util.Objects;

public record StudentCreatedData(
        Student student,
        PaymentId paymentId
) {
    public StudentCreatedData {
        Objects.requireNonNull(student, "student cannot be null");
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
    }
}
