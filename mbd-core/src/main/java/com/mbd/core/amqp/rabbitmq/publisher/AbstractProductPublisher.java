package com.mbd.core.amqp.rabbitmq.publisher;

import com.mbd.core.amqp.rabbitmq.configuration.RabbitMqPublisher;
import com.mbd.core.amqp.rabbitmq.model.Publisher;
import com.mbd.core.amqp.rabbitmq.model.RabbitMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractProductPublisher implements Publisher {

    private final RabbitMqPublisher rabbitMqPublisher;

    @Override
    public <T> void publish(RabbitMessage<T> message) {
        String exchange = this.buildExchangeName("default.exchange");
        String routingKey = "";

        this.rabbitMqPublisher.publish(exchange, routingKey, message);
    }

    public <T> RabbitMessage<T> createMessage(T body) {
        return RabbitMessage.<T>builder()
                .body(body)
                .headers(new HashMap<>())
                .build();
    }

    private String buildExchangeName(String exchangeName) {
        return this.getProduct() + "-" + exchangeName;
    }
}

