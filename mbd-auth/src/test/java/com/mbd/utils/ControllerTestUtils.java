package com.mbd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ControllerTestUtils {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private AmqpAdmin amqpAdmin;

    @MockitoBean
    private ConnectionFactory connectionFactory;

    @MockitoBean
    private HttpSecurity httpSecurity;

    public ResultActions executePost(String url, Object body) throws Exception {
        return this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","en-US")
                .content(this.objectMapper.writeValueAsString(body)));
    }

    public ResultActions executeGet(String url) throws Exception {
        return this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","en-US"));
    }

    public static ResultMatcher matchValidation(Class<?> clazz, String name) {
        var stringToMatch = format("code\":\"%s\",\"field\":\"%s\"", clazz.getSimpleName(), name);
        return content().string(containsString(stringToMatch));
    }
}
