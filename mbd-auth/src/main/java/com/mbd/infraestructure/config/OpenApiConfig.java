package com.mbd.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MBD Auth API")
                        .description("API para gerenciamento de autenticação e usuários do sistema MBD Menu")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipe MBD")
                                .email("contato@mbd.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8200/mbd/rest").description("Servidor de Desenvolvimento"),
                        new Server().url("https://api.mbd.com").description("Servidor de Produção")
                ));
    }

}
