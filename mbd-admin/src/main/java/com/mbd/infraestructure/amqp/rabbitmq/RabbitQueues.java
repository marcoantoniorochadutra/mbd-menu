package com.mbd.infraestructure.amqp.rabbitmq;


public interface RabbitQueues {

    record DevQueues() {
        public static final String DEBUG_QUEUE = "mbd-admin-debug-queue";
    }


}
