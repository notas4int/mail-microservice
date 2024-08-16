package org.artem.projects.mailregistry.kafka.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringSerializer;
import org.artem.projects.mailregistry.kafka.models.UserCredentialsMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, UserCredentialsMsg> emailProducerFactory(ObjectMapper mapper) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var producerFactory = new DefaultKafkaProducerFactory<String, UserCredentialsMsg>(configProps);
        producerFactory.setValueSerializer(new JsonSerializer<>(mapper));
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, UserCredentialsMsg> emailKafkaTemplate(
            ProducerFactory<String, UserCredentialsMsg> emailProducerFactory) {
        return new KafkaTemplate<>(emailProducerFactory);
    }
}
