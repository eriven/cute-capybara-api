package com.rizzler.capyapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI capyApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Capy API")
                        .description("Capybara content-feed REST API. Browse, filter, and discover capybara images.")
                        .version("v1.0.0"));
    }
}
