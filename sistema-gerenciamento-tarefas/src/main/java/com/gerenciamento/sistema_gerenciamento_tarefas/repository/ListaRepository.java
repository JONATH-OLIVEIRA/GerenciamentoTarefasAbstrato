package com.gerenciamento.sistema_gerenciamento_tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long> {
}
