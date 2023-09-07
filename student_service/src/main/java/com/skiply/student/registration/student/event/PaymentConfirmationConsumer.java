package com.skiply.student.registration.student.event;

import com.skiply.student.registration.common.model.kafka.PaymentConfirmationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationConsumer.class);

    @KafkaListener(topics = "payment-confirmed", groupId = "com.skiply.student.registration.student")
    public void consume(PaymentConfirmationEvent message) {
        LOGGER.info("Payment confirmation consumed Kafka message -> {}", message);
    }
}