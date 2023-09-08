package com.skiply.student.registration.payment.event;


import com.skiply.student.registration.common.model.kafka.PaymentConfirmationReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationReportEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationReportEventPublisher.class);

    public static final String TOPIC = "payment-confirmed-report";

    private final KafkaTemplate<String, PaymentConfirmationReportEvent> kafkaTemplate;

    public PaymentConfirmationReportEventPublisher(KafkaTemplate<String, PaymentConfirmationReportEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentConfirmationReportEvent event) {
        var key = event.paymentId();
        kafkaTemplate.send(TOPIC, key.value(), event);
        LOGGER.info("Payment confirm report producer produced the message {}", event);
    }
}