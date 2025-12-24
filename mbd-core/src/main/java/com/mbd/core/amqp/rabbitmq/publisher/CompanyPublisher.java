package com.mbd.core.amqp.rabbitmq.publisher;

import com.mbd.core.amqp.rabbitmq.configuration.RabbitMqPublisher;
import com.mbd.core.amqp.rabbitmq.model.RabbitEvent;
import org.springframework.stereotype.Component;


@Component
public class CompanyPublisher extends AbstractProductPublisher {

    public CompanyPublisher(RabbitMqPublisher rabbitMqPublisher) {
        super(rabbitMqPublisher);
    }

    @Override
    public String getProduct() {
        return "mbd-company";
    }

    public void publishDebug(RabbitEvent event) {
        var message = this.createMessage(event);
        this.publish(message);
    }

}
