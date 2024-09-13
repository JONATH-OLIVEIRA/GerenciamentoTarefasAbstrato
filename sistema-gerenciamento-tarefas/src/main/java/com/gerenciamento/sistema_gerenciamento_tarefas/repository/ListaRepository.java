package com.gerenciamento.sistema_gerenciamento_tarefas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long> {
	Optional<Lista> findByTitulo(String titulo);
}
