package org.artem.projects.mailregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableRedisRepositories
@EnableTransactionManagement
public class MailRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailRegistryApplication.class, args);
    }

}
