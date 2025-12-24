package com.mbd.core.amqp.rabbitmq.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqConnection {

    private String host;
    private int managementPort;
    private String username;
    private String password;
    private String prefix;

}

