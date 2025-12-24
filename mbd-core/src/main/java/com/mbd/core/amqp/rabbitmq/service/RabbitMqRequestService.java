package com.mbd.core.amqp.rabbitmq.service;

import com.google.gson.annotations.SerializedName;
import com.mbd.core.amqp.rabbitmq.model.RabbitMqConnection;
import com.mbd.core.utils.JsonUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.mbd.core.utils.FunctionUtils.nullSafeStream;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class RabbitMqRequestService {

    private final RestTemplate restTemplate;
    private final RabbitMqConnection rabbitConnection;

    private static final String HOST_PLACEHOLDER = "https://%s:%s/api/";
    private static final String EXCHANGE_URL = HOST_PLACEHOLDER + "exchanges";
    private static final String QUEUE_URL = HOST_PLACEHOLDER + "queues";

    public List<RabbitInfo> requestExchangesToDelete(Set<String> configuredExchangeNames) {
        var url = format(EXCHANGE_URL, this.rabbitConnection.getHost(), this.rabbitConnection.getManagementPort());

        var responseAsStream = this.executeRequest(url);

        return responseAsStream
                .filter(exchange -> this.rabbitConnection.getUsername().equals(exchange.getUserWhoPerformedAction()))
                .filter(exchange -> exchange.getName().startsWith(this.rabbitConnection.getPrefix()))
                .filter(exchange -> !configuredExchangeNames.contains(exchange.getName()))
                .toList();
    }

    public List<RabbitInfo> requestQueuesToDelete(Set<String> configuredQueueNames) {
        var url = format(QUEUE_URL, this.rabbitConnection.getHost(), this.rabbitConnection.getManagementPort());

        var responseAsStream = this.executeRequest(url);

        return responseAsStream
                .filter(exchange -> !configuredQueueNames.contains(exchange.getName()))
                .filter(exchange -> exchange.getName().startsWith(this.rabbitConnection.getPrefix()))
                .toList();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();

        String auth = this.rabbitConnection.getUsername() + ":" + this.rabbitConnection.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        headers.set("Authorization", "Basic " + encodedAuth);
        return headers;
    }

    private Stream<RabbitInfo> executeRequest(String url) {
        var headers = this.buildHeaders();

        var response = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        RabbitInfo[] resultArray = JsonUtils.fromJson(response.getBody(), RabbitInfo[].class);

        return nullSafeStream(resultArray);
    }

    @Data
    public static class RabbitInfo {
        private String name;

        @SerializedName("user_who_performed_action")
        private String userWhoPerformedAction;
    }
}
