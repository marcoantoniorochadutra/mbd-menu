package com.mbd.core.amqp.rabbitmq.configuration;

import com.mbd.core.amqp.rabbitmq.model.RabbitMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqPublisher {

    private final RabbitTemplate rabbitTemplate;

    public <T> void publish(String exchange, String routingKey, RabbitMessage<T> rabbitMessage) {

        if (isNull(rabbitMessage)) {
            throw new IllegalArgumentException("RabbitMessage não pode ser null");
        }

        try {
            log.debug("Publicando RabbitMessage para exchange: {}, routingKey: {}", exchange, routingKey);

            T body = rabbitMessage.getBody();
            Map<String, Object> headers = rabbitMessage.getHeaders();

            this.rabbitTemplate.convertAndSend(exchange, routingKey, body, msg -> {
                MessageProperties props = msg.getMessageProperties();
                props.setInferredArgumentType(rabbitMessage.getClass().getComponentType());
                props.setContentType("application/json");

                if (headers != null && !headers.isEmpty()) {
                    headers.forEach(props::setHeader);
                }

                return msg;
            });

            log.info("RabbitMessage publicado com sucesso para exchange: {}, routingKey: {}", exchange, routingKey);
        } catch (Exception e) {
            log.error("Erro ao publicar RabbitMessage para exchange: {}, routingKey: {}", exchange, routingKey, e);
            throw e;
        }
    }
}
