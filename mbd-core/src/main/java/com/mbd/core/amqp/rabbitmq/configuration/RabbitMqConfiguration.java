package com.mbd.core.amqp.rabbitmq.configuration;

import com.mbd.core.amqp.rabbitmq.model.RabbitMqConnection;
import com.mbd.core.amqp.rabbitmq.model.RabbitMqProperties;
import com.mbd.core.amqp.rabbitmq.model.RabbitMqProperties.RabbitExchangeConfig;
import com.mbd.core.amqp.rabbitmq.model.RabbitMqProperties.RabbitQueueConfig;
import com.mbd.core.amqp.rabbitmq.service.RabbitMqCleanupService;
import com.mbd.core.utils.FunctionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mbd.core.utils.FunctionUtils.nullSafeStream;
import static java.util.Objects.nonNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {RabbitMqProperties.class, RabbitMqConnection.class})
public class RabbitMqConfiguration {

    private static final String DOT = ".....   ";

    private final RabbitMqCleanupService rabbitMqCleanupService;
    private final RabbitMqProperties rabbitProperties;
    private final AmqpAdmin amqpAdmin;


    @Bean
    public Map<String, Exchange> createExchanges() {
        var exchangeMap = new HashMap<String, Exchange>();

        Set<String> configuredExchangeNames = this.rabbitProperties.getExchange().stream()
                .map(exchange -> this.buildValidName(exchange.getName()))
                .collect(Collectors.toSet());

        if (this.rabbitProperties.isAutoCleanup()) {
            this.rabbitMqCleanupService.clearExchanges(configuredExchangeNames);
        }

        nullSafeStream(this.rabbitProperties.getExchange()).forEach(exchangeConfig -> {
            var exchangeName = this.buildValidName(exchangeConfig.getName());

            if (exchangeConfig.getType().isDirect()) {
                var directExchange = ExchangeBuilder.directExchange(exchangeName).durable(true).build();
                this.declareExchange(directExchange, exchangeMap);
            }

            if (exchangeConfig.getType().isFanout()) {
                var fanoutExchange = ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();
                this.declareExchange(fanoutExchange, exchangeMap);
            }

            if (exchangeConfig.getType().isTopic()) {
                var topicExchange = ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
                this.declareExchange(topicExchange, exchangeMap);
            }
        });

        return exchangeMap;
    }

    private void declareExchange(Exchange exchange, Map<String, Exchange> exchangeMap) {
        this.amqpAdmin.declareExchange(exchange);
        exchangeMap.put(exchange.getName(), exchange);
        log.debug("{} Exchange criada/atualizada: {}", DOT, exchange.getName());
    }

    @Bean
    public Map<String, Queue> createQueues() {

        Set<String> configuredQueueNames = nullSafeStream(this.rabbitProperties.getExchange())
                .map(RabbitExchangeConfig::getQueues)
                .flatMap(FunctionUtils::nullSafeStream)
                .map(queue -> this.buildValidName(queue.getName()))
                .collect(Collectors.toSet());

        if (this.rabbitProperties.isAutoCleanup()) {
            this.rabbitMqCleanupService.clearQueues(configuredQueueNames);
        }

        var createQueues = new HashMap<String, Queue>();
        nullSafeStream(this.rabbitProperties.getExchange()).forEach(exchange ->
                nullSafeStream(exchange.getQueues()).forEach(queue -> {
                    var exchangeName = this.buildValidName(queue.getName());

                    Map<String, Object> arguments = getStringObjectMap(queue);

                    var queueToDeclare = QueueBuilder.durable(exchangeName)
                            .withArguments(arguments)
                            .build();

                    this.amqpAdmin.declareQueue(queueToDeclare);
                    createQueues.put(exchangeName, queueToDeclare);

                    log.debug("{} Queue criada/atualizada: {}", DOT, exchangeName);
                }));


        return createQueues;
    }

    private static Map<String, Object> getStringObjectMap(RabbitQueueConfig queue) {
        Map<String, Object> arguments = new HashMap<>();

        if (queue.getTtl() != null) {
            arguments.put("x-message-ttl", queue.getTtl());
        }
        if (queue.getDlx() != null) {
            arguments.put("x-dead-letter-exchange", queue.getDlx());
        }
        if (queue.getDlqRoutingKey() != null) {
            arguments.put("x-dead-letter-routing-key", queue.getDlqRoutingKey());
        }
        return arguments;
    }

    @Bean
    public List<Binding> createBindings(Map<String, Queue> queueMap, Map<String, Exchange> exchangeMap) {
        List<Binding> bindingList = new ArrayList<>();

        nullSafeStream(this.rabbitProperties.getExchange()).forEach(exchange -> {
            var exchangeName = this.buildValidName(exchange.getName());

            nullSafeStream(exchange.getBindings()).forEach(bindingConfig -> {
                Queue queue = queueMap.get(this.buildValidName(bindingConfig.getQueue()));
                Exchange ex = exchangeMap.get(this.buildValidName(bindingConfig.getExchange()));

                if (nonNull(queue) && nonNull(ex) && exchangeName.equals(ex.getName())) {
                    Binding binding = BindingBuilder
                            .bind(queue)
                            .to(ex)
                            .with(bindingConfig.getRoutingKey())
                            .noargs();

                    this.amqpAdmin.declareBinding(binding);
                    bindingList.add(binding);
                    log.debug("{} Binding criado: {} -> {} [{}]", DOT,
                            bindingConfig.getQueue(),
                            bindingConfig.getExchange(),
                            bindingConfig.getRoutingKey());
                } else {
                    log.warn("{} Binding não criado devido a configuração inválida: {} | {} -> {} [{}]", DOT,
                            exchangeName,
                            bindingConfig.getQueue(),
                            bindingConfig.getExchange(),
                            bindingConfig.getRoutingKey());
                }
            });

        });

        return bindingList;
    }

    private String buildValidName(String queue) {
        return this.rabbitProperties.getPrefix() + "-" + queue;
    }

}
