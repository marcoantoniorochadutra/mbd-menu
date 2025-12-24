package com.mbd.company;

import com.mbd.infraestructure.config.CoreConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {
        CoreConfig.class
})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
