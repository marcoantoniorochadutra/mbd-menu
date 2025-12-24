package com.mbd.admin.dev.application;

import com.mbd.admin.dev.usecase.DevUseCase;
import com.mbd.core.amqp.rabbitmq.publisher.CompanyPublisher;
import com.mbd.core.amqp.rabbitmq.publisher.DeliveryPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DevAppService implements DevUseCase {

    private final CompanyPublisher companyPublisher;
    private final DeliveryPublisher deliveryPublisher;

    @Override
    public void publishDebugEvent(DebugEvent debugEvent) {
        this.deliveryPublisher.publishDebug(debugEvent);
        this.companyPublisher.publishDebug(debugEvent);
    }


    @Override
    public void when(DebugEvent debugEvent) {
        log.info("Evento recebido com sucesso: {}", debugEvent);
    }
}
