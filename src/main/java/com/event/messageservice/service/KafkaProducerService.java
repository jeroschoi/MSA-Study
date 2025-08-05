package com.event.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaProducerTemplate;

    public void sendMessage(String topic, String message) {
        kafkaProducerTemplate.send(topic, message);
    }
}
