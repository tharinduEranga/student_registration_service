package com.skiply.student.registration.reporting.event;

import com.skiply.student.registration.common.model.kafka.StudentDataUpdateReportEvent;
import com.skiply.student.registration.reporting.service.UpdateStudentDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateStudentDetailsConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStudentDetailsConsumer.class);

    private final UpdateStudentDataService updateStudentDataService;

    public UpdateStudentDetailsConsumer(UpdateStudentDataService updateStudentDataService) {
        this.updateStudentDataService = updateStudentDataService;
    }

    @KafkaListener(topics = "update-student-data-report", groupId = "com.skiply.student.registration.report")
    public void consume(StudentDataUpdateReportEvent event) {
        try {
            LOGGER.info("Student data update report consumed Kafka message -> {}", event);
            updateStudentDataService.execute(event.paymentId(), event.student());
            LOGGER.info("Student data update report consumed response");
        } catch (Exception e) {
            LOGGER.error("Student data update report consume error -> {}", e.getMessage(), e);
        }
    }
}