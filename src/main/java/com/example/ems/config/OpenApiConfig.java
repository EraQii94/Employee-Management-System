package com.example.ems.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .description("REST API for managing employees, departments and projects")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Abdulrahman Eraky")
                                .email("abdulrahmanaleraky@gmail.com")));
    }
}