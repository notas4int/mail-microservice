package org.artem.projects.mailsender.producers;

import lombok.RequiredArgsConstructor;
import org.artem.projects.mailsender.models.ConfirmationCodeMsg;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeProducer {
    private final KafkaTemplate<String, ConfirmationCodeMsg> kafkaTemplate;

    public void sendConfirmationCode(String topic, ConfirmationCodeMsg code) {
        kafkaTemplate.send(topic, code);
    }
}
