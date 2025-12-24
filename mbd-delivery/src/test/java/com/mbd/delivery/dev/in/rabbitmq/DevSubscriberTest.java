package com.mbd.delivery.dev.in.rabbitmq;

import com.mbd.core.amqp.rabbitmq.publisher.DeliveryPublisher;
import com.mbd.delivery.dev.usecase.DevUseCase;
import com.mbd.delivery.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.delivery.utils.AmqpTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DevSubscriberTest extends AmqpTestUtils {

    @MockitoBean
    private DevUseCase devUseCase;

    @Autowired
    private DeliveryPublisher deliveryPublisher;


    @Test
    void deveEnviarMensagemComPublisherEValidarNoListener() {

        DebugEvent db = DebugEvent.empty();

        this.deliveryPublisher.publishDebug(db);

        this.checkService(() ->
                verify(this.devUseCase, times(1)).when(any()));

    }


}
