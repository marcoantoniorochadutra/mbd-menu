package com.mbd.core.amqp.rabbitmq.converter;

import com.mbd.core.amqp.rabbitmq.model.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import tools.jackson.databind.JavaType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class RabbitMessageConverter extends JacksonJsonMessageConverter {

    @Override
    public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {

        if (isNull(conversionHint)) {
            conversionHint = message.getMessageProperties().getInferredArgumentType();
        }

        if (conversionHint instanceof ParameterizedType pt) {
            Type rawType = pt.getRawType();

            if (rawType.equals(RabbitMessage.class)) {
                return this.convertToRabbitMessage(message, pt);
            }
        }

        if (conversionHint instanceof Class<?> clazz) {
            log.info("ConversionHint é Class: {}", clazz);
            if (RabbitMessage.class.isAssignableFrom(clazz)) {
                log.warn("Detectado RabbitMessage sem generics, tentando inferir tipo do JSON");
                return this.convertToRabbitMessageWithoutGenerics(message);
            }
        }

        log.info("Usando conversão padrão");
        return super.fromMessage(message, conversionHint);
    }

    private RabbitMessage<?> convertToRabbitMessage(Message message, ParameterizedType parameterizedType) {
        try {
            Map<String, Object> headers = this.extractHeaders(message.getMessageProperties());

            Object body = this.convertBody(message, parameterizedType);

            return RabbitMessage.builder()
                    .body(body)
                    .headers(headers)
                    .build();

        } catch (Exception e) {
            throw new MessageConversionException("Falha na conversão", e);
        }
    }

    private Object convertBody(Message message, ParameterizedType parameterizedType) {
        Type[] typeArgs = parameterizedType.getActualTypeArguments();

        if (typeArgs.length > 0) {
            Type bodyType = typeArgs[0];

            JavaType javaType = this.objectMapper.getTypeFactory().constructType(bodyType);

            byte[] bytes = message.getBody();

            if (bytes.length > 0) {
                return this.objectMapper.readValue(bytes, javaType);
            }
        }
        return null;
    }

    private RabbitMessage<?> convertToRabbitMessageWithoutGenerics(Message message) {
        try {
            log.info("Convertendo RabbitMessage sem informação de generics");


            Map<String, Object> headers = this.extractHeaders(message.getMessageProperties());

            byte[] bytes = message.getBody();
            Object body = null;

            if (bytes.length > 0) {
                String jsonBody = new String(bytes);
                log.info("JSON: {}", jsonBody);
                body = this.objectMapper.readValue(bytes, Object.class);
                log.info("Body deserializado como Object: {}", body);
            }

            return RabbitMessage.builder()
                    .body(body)
                    .headers(headers)
                    .build();

        } catch (Exception e) {
            log.error("Erro ao converter RabbitMessage sem generics", e);
            throw new MessageConversionException("Falha na conversão", e);
        }
    }

    private Map<String, Object> extractHeaders(MessageProperties properties) {
        Map<String, Object> headers = new HashMap<>();

        if (isNull(properties)) {
            return headers;
        }

        headers.putAll(properties.getHeaders());

        this.addIfNotNull(headers, "content-type", properties.getContentType());
        this.addIfNotNull(headers, "content-encoding", properties.getContentEncoding());
        this.addIfNotNull(headers, "message-id", properties.getMessageId());
        this.addIfNotNull(headers, "correlation-id", properties.getCorrelationId());
        this.addIfNotNull(headers, "timestamp", properties.getTimestamp());
        this.addIfNotNull(headers, "type", properties.getType());
        this.addIfNotNull(headers, "user-id", properties.getUserId());
        this.addIfNotNull(headers, "app-id", properties.getAppId());

        return headers;
    }

    private void addIfNotNull(Map<String, Object> headers, String key, Object value) {
        if (nonNull(value)) {
            headers.put(key, value);
        }
    }
}
