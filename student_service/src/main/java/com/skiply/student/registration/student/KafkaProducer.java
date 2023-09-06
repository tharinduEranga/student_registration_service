package com.skiply.student.registration.student;


import com.skiply.student.registration.common.model.kafka.MyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    public static final String TOPIC = "student-created";

    private final KafkaTemplate<String, MyEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, MyEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFlightEvent(MyEvent event) {
        String key = event.message();
        kafkaTemplate.send(TOPIC, key, event);
        LOGGER.info("Producer produced the message {}", event);
        // write your handlers and post-processing logic, based on your use case
    }
}