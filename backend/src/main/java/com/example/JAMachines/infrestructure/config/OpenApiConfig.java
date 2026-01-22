package com.example.JAMachines.infrestructure.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - First Stop")
                        .version("1.0.0")
                        .description("spec of the First Stop API")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Eliel Raposo")
                                .email("silvaeliel940@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")));
    }
}
