package com.mbd.core.amqp.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitMessage<T> implements Serializable {

    private transient T body;

    @Builder.Default
    private transient Map<String, Object> headers = new HashMap<>();

    public Object getHeader(String key) {
        return this.headers != null ? this.headers.get(key) : null;
    }

    public boolean hasHeader(String key) {
        return this.headers != null && this.headers.containsKey(key);
    }
}
