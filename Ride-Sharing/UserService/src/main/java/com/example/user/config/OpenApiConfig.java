package com.example.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0",
                description = "Manages user profiles and roles"
        ),
        servers = @Server(url = "http://localhost:8081", description = "User Service")
)
public class OpenApiConfig {
}