package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Payment Service API",
                version = "1.0",
                description = "Handles payment processing and transactions"
        ),
        servers = @Server(url = "http://localhost:8083", description = "Payment Service")
)
public class OpenApiConfig {}