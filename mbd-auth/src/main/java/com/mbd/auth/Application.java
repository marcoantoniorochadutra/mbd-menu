package com.mbd.auth;

import com.mbd.infraestructure.config.AuthConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AuthConfig.class)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}