package com.skiply.student.registration.student.event;

import com.skiply.student.registration.common.model.StudentStatus;
import com.skiply.student.registration.common.model.kafka.PaymentConfirmationEvent;
import com.skiply.student.registration.student.service.StudentModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationConsumer.class);

    private final StudentModifier studentModifier;

    public PaymentConfirmationConsumer(StudentModifier studentModifier) {
        this.studentModifier = studentModifier;
    }

    @KafkaListener(topics = "payment-confirmed", groupId = "com.skiply.student.registration.student")
    public void consume(PaymentConfirmationEvent message) {
        try {
            LOGGER.info("Payment confirmation consumed Kafka message -> {}", message);
            var status = switch (message.status()) {
                case SUCCEEDED -> StudentStatus.REGISTERED;
                case FAILED -> StudentStatus.UNREGISTERED;
                default -> StudentStatus.PENDING_REGISTRATION;
            };
            final var student = studentModifier.updateStatus(message.studentId(), status);
            LOGGER.info("Payment confirmation consumed, student -> {}", student);
        } catch (Exception e) {
            LOGGER.error("Payment confirmation consume error -> {}", e.getMessage(), e);
        }
    }
}