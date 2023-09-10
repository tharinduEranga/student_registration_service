package com.skiply.student.registration.student.event.publisher;


import com.skiply.student.registration.common.model.kafka.StudentDataUpdateReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentUpdateDataReportEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentUpdateDataReportEventPublisher.class);

    public static final String TOPIC = "update-student-data-report";

    private final KafkaTemplate<String, StudentDataUpdateReportEvent> kafkaTemplate;

    public StudentUpdateDataReportEventPublisher(KafkaTemplate<String, StudentDataUpdateReportEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(StudentDataUpdateReportEvent event) {
        var key = event.paymentId();
        kafkaTemplate.send(TOPIC, key.value(), event);
        LOGGER.info("Payment confirm report producer produced the message {}", event);
    }
}