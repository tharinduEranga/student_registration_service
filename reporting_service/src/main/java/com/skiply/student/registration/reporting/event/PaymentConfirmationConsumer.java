package com.skiply.student.registration.reporting.event;

import com.skiply.student.registration.common.model.kafka.PaymentConfirmationReportEvent;
import com.skiply.student.registration.reporting.mapper.ReportApiModelMapper;
import com.skiply.student.registration.reporting.service.UpdateReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConfirmationConsumer.class);

    private final UpdateReportService updateReportService;

    public PaymentConfirmationConsumer(UpdateReportService updateReportService) {
        this.updateReportService = updateReportService;
    }

    @KafkaListener(topics = "payment-confirmed-report", groupId = "com.skiply.student.registration.report")
    public void consume(PaymentConfirmationReportEvent event) {
        try {
            LOGGER.info("Payment confirmation report consumed Kafka message -> {}", event);
            final var consumedResponse = updateReportService
                    .execute(ReportApiModelMapper.getPaymentFromPaymentConfirmationEvent(event));
            LOGGER.info("Payment confirmation report consumed response -> {}", consumedResponse);
        } catch (Exception e) {
            LOGGER.error("Payment confirmation report consume error -> {}", e.getMessage(), e);
        }
    }
}