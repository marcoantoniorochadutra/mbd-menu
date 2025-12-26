package com.mbd.utils;

import org.awaitility.core.ThrowingRunnable;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
public class AmqpTestUtils {

    @Autowired
    protected RabbitTemplate template;

    @Autowired
    protected RabbitAdmin admin;

    protected void checkService(ThrowingRunnable assertion) {
        await().atMost(3, TimeUnit.SECONDS).untilAsserted(assertion);
    }
}
