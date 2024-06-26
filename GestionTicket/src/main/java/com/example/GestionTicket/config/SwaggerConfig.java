package com.example.GestionTicket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                .title("Gestion Ticket")
                        .description("fcds")
                        .version("0.0.1")
                        .termsOfService("http://swagger.io/terms")
                        .contact(new Contact().name("Amadou Niangaly").email("amadouniangaly16@gmail.com"))
                );
    }
}