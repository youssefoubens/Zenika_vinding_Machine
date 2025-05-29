package com.zenika.vendingmachine.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
                        .title("Vending Machine API")
                        .description("API for managing vending machine operations including coin insertion, product selection, purchase, and transaction status.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vending Machine Dev Team")
                                .email("support@vendingmachine.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
