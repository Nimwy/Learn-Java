package com.example.remoteit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI remoteItOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Remote IT Job API")
                .version("1.0")
                .description("Bài 12: phân trang, lọc, index, CORS và OpenAPI"));
    }
}
