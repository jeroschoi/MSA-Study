package com.event.messageservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

   @Value("${kafka.consumer.bootstrap.servers}")
   private String bootstrapConsumerServers;

   @Value("${kafka.producer.bootstrap.servers}")
   private String bootstrapProducerServers;

   @Bean
   public ProducerFactory<String, Object> kafkaProducerFactory() {
       Map<String, Object> config = new HashMap<>();
       config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapConsumerServers);
       config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
       config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

       return new DefaultKafkaProducerFactory<>(config);
   }

   @Bean
   public ConsumerFactory<String, Object> kafkaConsumerFactory() {
       Map<String, Object> config = new HashMap<>();
       config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapProducerServers);
       config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
       config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

       return new DefaultKafkaConsumerFactory<>(config);
   }

   @Bean
   public KafkaTemplate<String, Object> kafkaProducerTemplate() {
       return new KafkaTemplate<>(kafkaProducerFactory());
   }
}


