package org.artem.projects.mailsender.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.artem.projects.mailsender.models.UserCredentialsMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Configuration
@EnableKafka
public class ConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, UserCredentialsMsg> credentialsConsumerFactory(ObjectMapper mapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(TYPE_MAPPINGS, "org.artem.projects.mailregistry.kafka.models.UserCredentialsMsg:org.artem.projects.mailsender.models.UserCredentialsMsg");

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

//        JsonDeserializer<UserCredentialsMsg> payloadJsonDeserializer
//                = new JsonDeserializer<>(UserCredentialsMsg.class, false);
//        payloadJsonDeserializer.addTrustedPackages("org.artem.projects.mailsender.models");
//        payloadJsonDeserializer.addTrustedPackages("org.artem.projects.mailregistry.kafka.models");
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class);

        var consumer = new DefaultKafkaConsumerFactory<String, UserCredentialsMsg>(props);
        consumer.setValueDeserializer(new JsonDeserializer<>(mapper));
        return consumer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCredentialsMsg> credentialsListenerContainerFactory(
            ConsumerFactory<String, UserCredentialsMsg> credentialsConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserCredentialsMsg> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(credentialsConsumerFactory);
//        factory.setBatchListener(false);
//        factory.setRecordMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
