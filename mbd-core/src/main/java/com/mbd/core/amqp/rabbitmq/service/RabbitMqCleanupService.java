package com.mbd.core.amqp.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.mbd.core.utils.FunctionUtils.nullSafeStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqCleanupService {

    private static final String DOT = ".....   ";

    private final RabbitMqRequestService rabbitMqRequestService;
    private final AmqpAdmin amqpAdmin;


    public void clearExchanges(Set<String> configuredExchangeNames) {
        var exchangesToDelete = this.rabbitMqRequestService.requestExchangesToDelete(configuredExchangeNames);

        nullSafeStream(exchangesToDelete).forEach(exchangeInfo ->
                this.deleteExchange(exchangeInfo.getName()));
    }

    private void deleteExchange(String exchangeName) {
        try {
            this.amqpAdmin.deleteExchange(exchangeName);
            log.info("{} Exchange deletada: {}", DOT, exchangeName);
        } catch (Exception e) {
            log.error("{} Erro ao deletar exchange {}: {}", DOT, exchangeName, e.getMessage());
        }
    }

    public void clearQueues(Set<String> configuredQueueNames) {
        var queuesToDelete = this.rabbitMqRequestService.requestQueuesToDelete(configuredQueueNames);

        nullSafeStream(queuesToDelete).forEach(exchangeInfo ->
                this.deleteQueue(exchangeInfo.getName()));
    }

    private void deleteQueue(String queueName) {
        try {
            this.amqpAdmin.deleteQueue(queueName);
            log.info("{} Queue deletada: {}", DOT, queueName);
        } catch (Exception e) {
            log.error("{} Erro ao deletar queue {}: {}", DOT, queueName, e.getMessage());
        }
    }
}
