package org.artem.projects.mailsender.consumers;

import lombok.RequiredArgsConstructor;
import org.artem.projects.mailsender.models.ConfirmationCodeMsg;
import org.artem.projects.mailsender.models.UserCredentialsMsg;
import org.artem.projects.mailsender.producers.ConfirmationCodeProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class MailConsumer {
    private final ConfirmationCodeProducer confirmationCodeProducer;
    private final AtomicInteger counter = new AtomicInteger(100000);

    @KafkaListener(groupId = "mail-sender", topics = "mail.registry.user.credentials",
            containerFactory = "credentialsListenerContainerFactory")
    public void consume(UserCredentialsMsg msg) {
        System.out.println("Consumed message: " + msg);
        if (counter.get() < 1000000)
            counter.incrementAndGet();

        ConfirmationCodeMsg codeMsg
                = new ConfirmationCodeMsg(msg.getEmail(), msg.getPassword(), counter.get());
        confirmationCodeProducer.sendConfirmationCode("mail.sender.confirmation-code", codeMsg);
    }
}
