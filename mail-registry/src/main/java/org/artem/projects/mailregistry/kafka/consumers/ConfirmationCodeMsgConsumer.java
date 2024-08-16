package org.artem.projects.mailregistry.kafka.consumers;

import lombok.RequiredArgsConstructor;
import org.artem.projects.mailregistry.kafka.models.ConfirmationCodeMsg;
import org.artem.projects.mailregistry.mappers.UserMapper;
import org.artem.projects.mailregistry.models.CacheUser;
import org.artem.projects.mailregistry.models.exceptions.UserWithSuchEmailAlreadyExists;
import org.artem.projects.mailregistry.repositories.CacheUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeMsgConsumer {
    private static final Logger log = LoggerFactory.getLogger(ConfirmationCodeMsgConsumer.class);
    private final CacheUserRepository cacheUserRepository;
    private final UserMapper userMapper;

    @KafkaListener(groupId = "mail-registry", topics = "mail.sender.confirmation-code",
            containerFactory = "confirmationCodeListenerContainerFactory")
    @Transactional
    public void listen(ConfirmationCodeMsg message) {
        System.out.println("Consumed message: " + message);
        if (cacheUserRepository.existsCacheUserByEmail(message.getEmail()))
            throw new UserWithSuchEmailAlreadyExists("User with email '" + message.getEmail() + "' already exists");
        CacheUser user = userMapper.convertKafkaRequestToCacheUser(message);
        cacheUserRepository.save(user);
    }
}
