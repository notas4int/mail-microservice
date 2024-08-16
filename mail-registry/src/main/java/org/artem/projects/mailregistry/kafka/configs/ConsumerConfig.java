package org.artem.projects.mailregistry.kafka.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.artem.projects.mailregistry.kafka.models.ConfirmationCodeMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, ConfirmationCodeMsg> confirmationCodeConsumerFactory(ObjectMapper mapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "org.artem.projects.mailsender.models.ConfirmationCodeMsg:org.artem.projects.mailregistry.kafka.models.ConfirmationCodeMsg");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        var consumer = new DefaultKafkaConsumerFactory<String, ConfirmationCodeMsg>(props);
        consumer.setValueDeserializer(new JsonDeserializer<>(mapper));
        return consumer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConfirmationCodeMsg> confirmationCodeListenerContainerFactory(
            ConsumerFactory<String, ConfirmationCodeMsg> confirmationCodeConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ConfirmationCodeMsg> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(confirmationCodeConsumerFactory);
        return factory;
    }
}
