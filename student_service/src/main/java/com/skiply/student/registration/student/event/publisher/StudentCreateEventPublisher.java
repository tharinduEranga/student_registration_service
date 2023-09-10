package com.skiply.student.registration.student.event.publisher;

import com.skiply.student.registration.common.model.kafka.StudentCreatedReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentCreateEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCreateEventPublisher.class);

    public static final String TOPIC = "student-created-report";

    private final KafkaTemplate<String, StudentCreatedReportEvent> kafkaTemplate;

    public StudentCreateEventPublisher(KafkaTemplate<String, StudentCreatedReportEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(StudentCreatedReportEvent event) {
        var key = event.student().id();
        kafkaTemplate.send(TOPIC, key.value(), event);
        LOGGER.info("Student created event publisher published the message {}", event);
    }
}