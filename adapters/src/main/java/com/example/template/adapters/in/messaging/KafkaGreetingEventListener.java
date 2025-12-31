package com.example.template.adapters.in.messaging;

import com.example.template.domain.events.GreetingCreatedEvent;
import com.example.template.domain.ports.in.GreetingEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaGreetingEventListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaGreetingEventListener.class);

    private final GreetingEventHandler eventHandler;

    public KafkaGreetingEventListener(GreetingEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(
            topics = "greeting-events",
            groupId = "${spring.kafka.consumer.group-id:greeting-service-group}",
            containerFactory = "greetingEventListenerFactory")
    public void onGreetingCreated(GreetingCreatedEvent event) {
        logger.info("Received greeting created event: {}", event.eventId());
        try {
            eventHandler.handle(event);
        } catch (Exception e) {
            logger.error("Error handling event {}: {}", event.eventId(), e.getMessage(), e);
            throw e;
        }
    }
}
