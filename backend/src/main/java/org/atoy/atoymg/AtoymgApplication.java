package org.atoy.atoymg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info; 
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class AtoymgApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtoymgApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                    .info(new Info().title("atoymg")
                    .description("Project Description")
                    .version("0.0.1-SNAPSHOT"));
    }
}
