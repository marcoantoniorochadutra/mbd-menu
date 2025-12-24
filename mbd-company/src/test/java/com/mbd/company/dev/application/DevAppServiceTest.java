package com.mbd.company.dev.application;

import com.mbd.company.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.core.amqp.rabbitmq.publisher.AdminPublisher;
import com.mbd.core.amqp.rabbitmq.publisher.DeliveryPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitTest - DevAppService")
class DevAppServiceTest {

    @Mock
    private DeliveryPublisher deliveryPublisher;

    @Mock
    private AdminPublisher adminPublisher;

    @InjectMocks
    private DevAppService devAppService;

    @Test
    @DisplayName("Should publish debug event")
    void shouldPublishDebugEvents() {
        var debugEvent = DebugEvent.empty();
        debugEvent.addMessage("message", "Test");

        this.devAppService.publishDebugEvent(debugEvent);

        verify(this.adminPublisher).publishDebug(debugEvent);
        verify(this.deliveryPublisher).publishDebug(debugEvent);

    }

}
