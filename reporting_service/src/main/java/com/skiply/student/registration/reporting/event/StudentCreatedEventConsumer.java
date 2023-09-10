package com.skiply.student.registration.reporting.event;

import com.skiply.student.registration.common.model.kafka.StudentCreatedReportEvent;
import com.skiply.student.registration.reporting.service.CreateInitialReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentCreatedEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCreatedEventConsumer.class);

    private final CreateInitialReportService createInitialReportService;

    public StudentCreatedEventConsumer(CreateInitialReportService createInitialReportService) {
        this.createInitialReportService = createInitialReportService;
    }

    @KafkaListener(topics = "student-created-report", groupId = "com.skiply.student.registration.report")
    public void consume(StudentCreatedReportEvent event) {
        try {
            LOGGER.info("Student creation report consumed Kafka message -> {}", event);
            createInitialReportService.execute(event.student());
            LOGGER.info("Student creation report consumed successfully");
        } catch (Exception e) {
            LOGGER.error("Student creation report consume error -> {}", e.getMessage(), e);
        }
    }
}