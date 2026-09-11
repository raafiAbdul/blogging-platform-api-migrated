package com.example.blogging_platform_api_migrated.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "raafiAbdul",
                        email = "rafrafyunos@gmail.com"
                ),
                title = "Blogging Platform API - raafiAbdul",
                description = "A simple CRUD RESTful API for blogs that demonstrates " +
                        "usage Spring Boot and Spring Data JPA."
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Deployed ENV",
                        url = "https://blogging-platform-api-migrated-production.up.railway.app"
                )
        }
)
public class OpenApiConfig {

}
