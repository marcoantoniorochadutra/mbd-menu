package com.mbd.admin.dev.application;

import com.mbd.admin.dev.usecase.DevUseCase.DebugEvent;
import com.mbd.core.amqp.rabbitmq.publisher.CompanyPublisher;
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
    private CompanyPublisher companyPublisher;

    @InjectMocks
    private DevAppService devAppService;

    @Test
    @DisplayName("Should publish debug event")
    void shouldPublishDebugEvents() {

        var debugEvent = DebugEvent.empty();
        debugEvent.addMessage("message", "Test");

        this.devAppService.publishDebugEvent(debugEvent);

        verify(this.companyPublisher).publishDebug(debugEvent);
        verify(this.deliveryPublisher).publishDebug(debugEvent);
    }

}
