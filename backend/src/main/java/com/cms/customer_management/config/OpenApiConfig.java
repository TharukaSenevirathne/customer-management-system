package com.cms.customer_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Management System API")
                        .description("REST APIs for managing customers, bulk Excel uploads, pagination, sorting, and validation")
                        .version("1.0.0"));
    }
}
