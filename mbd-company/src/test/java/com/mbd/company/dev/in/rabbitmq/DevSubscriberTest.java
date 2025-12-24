package com.mbd.company.dev.in.rabbitmq;

import com.mbd.company.dev.usecase.DevUseCase;
import com.mbd.core.amqp.rabbitmq.publisher.CompanyPublisher;
import com.mbd.company.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.company.utils.AmqpTestUtils;
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
    private CompanyPublisher companyPublisher;

    @Test
    @DisplayName("Should receive debug event")
    void shouldReceiveDebugEvent() {
        DebugEvent db = DebugEvent.empty();

        this.companyPublisher.publishDebug(db);

        this.checkService(() ->
                verify(this.devUseCase, times(1)).when(any()));
    }
}
