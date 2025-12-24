package com.mbd.company.dev.usecase;

import com.mbd.core.amqp.rabbitmq.model.RabbitEvent;

import java.util.HashMap;
import java.util.Map;

public interface DevUseCase {

    void publishDebugEvent(DebugEvent debugEvent);

    void when(DebugEvent debugEvent);

    record DebugEvent(Map<String, Object> message) implements RabbitEvent {
        public static DebugEvent empty() {
            return new DebugEvent(new HashMap<>());
        }

        public void addMessage(String key, String value) {
            this.message.put(key, value);
        }
    }
}
