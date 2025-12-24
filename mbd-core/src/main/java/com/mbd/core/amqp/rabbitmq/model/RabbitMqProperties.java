package com.mbd.core.amqp.rabbitmq.model;

import com.mbd.core.amqp.rabbitmq.model.enums.RabbitExchangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqProperties {

    private String prefix;
    private List<RabbitExchangeConfig> exchange = new ArrayList<>();
    private boolean autoCleanup = false;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RabbitExchangeConfig {
        private String name;
        private RabbitExchangeType type;
        private List<RabbitQueueConfig> queues;
        private List<RabbitBindingConfig> bindings;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RabbitQueueConfig {
        private String name;
        private String dlx;
        private String dlqRoutingKey;
        private Integer ttl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RabbitBindingConfig {
        private String queue;
        private String exchange;
        private String routingKey;
    }
}

