package com.mbd.admin.dev.in.rabbitmq;

import com.mbd.admin.dev.usecase.DevUseCase;
import com.mbd.admin.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.admin.utils.AmqpTestUtils;
import com.mbd.core.amqp.rabbitmq.publisher.AdminPublisher;
import org.junit.jupiter.api.DisplayName;
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
    private AdminPublisher adminPublisher;

    @Test
    @DisplayName("Should receive debug event")
    void shouldReceiveDebugEvent() {
        DebugEvent db = DebugEvent.empty();

        this.adminPublisher.publishDebug(db);

        this.checkService(() ->
                verify(this.devUseCase, times(1)).when(any()));
    }
}
