package com.gerenciamento.sistema_gerenciamento_tarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Defaul Server URL")})
@SpringBootApplication
public class SistemaGerenciamentoTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGerenciamentoTarefasApplication.class, args);
	}

}
