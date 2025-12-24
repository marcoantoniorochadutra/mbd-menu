package com.mbd.core.amqp.rabbitmq.publisher;

import com.mbd.core.amqp.rabbitmq.configuration.RabbitMqPublisher;
import com.mbd.core.amqp.rabbitmq.model.RabbitEvent;
import org.springframework.stereotype.Component;


@Component
public class DeliveryPublisher extends AbstractProductPublisher {

    public DeliveryPublisher(RabbitMqPublisher rabbitMqPublisher) {
        super(rabbitMqPublisher);
    }

    @Override
    public String getProduct() {
        return "mbd-delivery";
    }


    public void publishDebug(RabbitEvent event) {
        var message = this.createMessage(event);
        this.publish(message);
    }


}