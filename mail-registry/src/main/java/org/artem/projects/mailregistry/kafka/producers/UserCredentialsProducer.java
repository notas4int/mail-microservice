package org.artem.projects.mailregistry.kafka.producers;

import lombok.RequiredArgsConstructor;
import org.artem.projects.mailregistry.kafka.models.UserCredentialsMsg;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialsProducer {
    private final KafkaTemplate<String, UserCredentialsMsg> kafkaTemplate;

    public void send(String topic, UserCredentialsMsg userCredentialsMsg) {
        kafkaTemplate.send(topic, userCredentialsMsg);
    }
}
