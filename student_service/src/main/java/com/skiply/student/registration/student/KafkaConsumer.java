package com.skiply.student.registration.student;

import com.skiply.student.registration.common.model.kafka.MyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "student-created", groupId = "com.skiply.student.registration.student")
    public void flightEventConsumer(MyEvent message) {
        LOGGER.info("Consumer consume Kafka message -> {}", message);

        // write your handlers and post-processing logic, based on your use case
    }
}