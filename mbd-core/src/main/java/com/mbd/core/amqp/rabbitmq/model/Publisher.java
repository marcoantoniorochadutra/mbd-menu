package com.mbd.core.amqp.rabbitmq.model;


public interface Publisher {

    String getProduct();

    <T> void publish(RabbitMessage<T> message);

}

