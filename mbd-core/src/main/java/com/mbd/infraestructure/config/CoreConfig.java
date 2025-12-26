package com.mbd.infraestructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.mbd.core.amqp.rabbitmq.converter.RabbitMessageConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = {
        "com.mbd.core.amqp.rabbitmq",
        "com.mbd.core.adapter.handler",
        "com.mbd.core.utils"
})
public class CoreConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new RabbitMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter rabbitMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(rabbitMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               MessageConverter rabbitMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(rabbitMessageConverter);
        return factory;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean("returnMessageSource")
    public MessageSource returnMessageSource() {
        var messageSource = this.defaultMessageSource();
        messageSource.setBasename("classpath:/messages/i18n/return/messages");
        return messageSource;
    }

    @Bean("validationMessageSource")
    public MessageSource validationMessageSource() {
        var messageSource = this.defaultMessageSource();
        messageSource.setBasename("classpath:/messages/i18n/validation/messages");
        return messageSource;
    }

    @Bean("errorMessageSource")
    public MessageSource errorMessageSource() {
        var messageSource = this.defaultMessageSource();
        messageSource.setBasename("classpath:/messages/i18n/exception/messages");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        var validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.validationMessageSource());
        return validator;
    }

    private ReloadableResourceBundleMessageSource defaultMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
