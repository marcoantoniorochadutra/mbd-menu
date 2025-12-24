package com.mbd.core.amqp.rabbitmq.model.enums;

public enum RabbitExchangeType {
    FANOUT,
    DIRECT,
    TOPIC;


    public boolean isFanout() {
        return this.equals(FANOUT);
    }

    public boolean isDirect() {
        return this.equals(DIRECT);
    }

    public boolean isTopic() {
        return this.equals(TOPIC);
    }
}
