package com.example.template.adapters.out.messaging;

import com.example.template.domain.events.GreetingCreatedEvent;
import com.example.template.domain.ports.out.GreetingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaGreetingEventPublisher implements GreetingEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaGreetingEventPublisher.class);
    private static final String TOPIC = "greeting-events";

    private final KafkaTemplate<String, GreetingCreatedEvent> kafkaTemplate;

    public KafkaGreetingEventPublisher(KafkaTemplate<String, GreetingCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(GreetingCreatedEvent event) {
        logger.info("Publishing greeting created event: {}", event.eventId());
        kafkaTemplate
                .send(TOPIC, event.greetingId().toString(), event)
                .whenComplete(
                        (result, ex) -> {
                            if (ex != null) {
                                logger.error(
                                        "Failed to publish event {}: {}",
                                        event.eventId(),
                                        ex.getMessage());
                            } else {
                                logger.debug(
                                        "Event {} published to partition {} at offset {}",
                                        event.eventId(),
                                        result.getRecordMetadata().partition(),
                                        result.getRecordMetadata().offset());
                            }
                        });
    }
}
