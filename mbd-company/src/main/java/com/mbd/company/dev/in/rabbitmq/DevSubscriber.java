package com.mbd.company.dev.in.rabbitmq;

import com.mbd.company.dev.usecase.DevUseCase;
import com.mbd.company.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.core.amqp.rabbitmq.model.RabbitMessage;
import com.mbd.infraestructure.amqp.rabbitmq.RabbitQueues.DevQueues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DevSubscriber {

    private final DevUseCase devUseCase;

    @RabbitListener(queues = DevQueues.DEBUG_QUEUE)
    public void handleDevMessage(RabbitMessage<DebugEvent> message) {
        this.devUseCase.when(message.getBody());
    }
}
