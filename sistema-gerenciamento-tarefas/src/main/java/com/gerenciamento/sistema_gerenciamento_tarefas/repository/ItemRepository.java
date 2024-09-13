package com.gerenciamento.sistema_gerenciamento_tarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByListaId(Long listaId);
    List<Item> findByEstado(Estado estado);
    List<Item> findByPrioridade(Prioridade prioridade);
    List<Item> findByEstadoAndPrioridade(Estado estado, Prioridade prioridade);
}
