package com.mbd.delivery.dev.application;

import com.mbd.core.amqp.rabbitmq.publisher.AdminPublisher;
import com.mbd.core.amqp.rabbitmq.publisher.CompanyPublisher;
import com.mbd.delivery.dev.usecase.DevUseCase.DebugEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UnitTest - App Service - Dev")
class DevAppServiceTest {

    @Mock
    private CompanyPublisher companyPublisher;

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
        verify(this.companyPublisher).publishDebug(debugEvent);
    }

}
