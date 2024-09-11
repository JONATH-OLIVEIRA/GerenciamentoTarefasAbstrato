package com.gerenciamento.sistema_gerenciamento_tarefas_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gerenciamento de Tarefas API")
                        .version("1.0")
                        .description("API para gerenciamento de listas e itens de tarefas"));
    }
}