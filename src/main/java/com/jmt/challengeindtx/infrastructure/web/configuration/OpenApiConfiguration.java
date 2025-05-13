package com.jmt.challengeindtx.infrastructure.web.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Products Price API",
                version = "1.0.0",
                description = "API para cálculo de precio de productos"
        )
)
public class OpenApiConfiguration {
}
