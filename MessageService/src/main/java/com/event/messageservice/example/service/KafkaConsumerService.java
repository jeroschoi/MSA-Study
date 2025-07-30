package com.event.messageservice.example.service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    @KafkaListener(topics = "msa_test", groupId = "msaTest")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
