package com.gerenciamento.sistema_gerenciamento_tarefas_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Validated
    public static class WebMvcConfig implements WebMvcConfigurer {
        // Configurações adicionais
    }
}