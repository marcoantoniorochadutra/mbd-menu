package com.mbd.infraestructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
        CoreConfig.class
})
@ComponentScan(value = "com.mbd.infraestructure")
@Configuration
public class AuthConfig {
}
