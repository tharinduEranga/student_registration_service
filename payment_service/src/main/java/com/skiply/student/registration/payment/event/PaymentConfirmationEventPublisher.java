package com.skiply.student.registration.payment.event;


import com.skiply.student.registration.common.model.kafka.PaymentConfirmationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationEventPublisher.class);

    public static final String TOPIC = "payment-confirmed";

    private final KafkaTemplate<String, PaymentConfirmationEvent> kafkaTemplate;

    public PaymentConfirmationEventPublisher(KafkaTemplate<String, PaymentConfirmationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentConfirmationEvent event) {
        var key = event.paymentId();
        kafkaTemplate.send(TOPIC, key.value(), event);
        LOGGER.info("Producer produced the message {}", event);
    }
}