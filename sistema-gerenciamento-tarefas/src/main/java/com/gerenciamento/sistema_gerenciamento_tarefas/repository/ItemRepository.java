package com.gerenciamento.sistema_gerenciamento_tarefas.repository;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByListaId(Long listaId);
    List<Item> findByEstado(String estado);
    List<Item> findByPrioridade(Integer prioridade);
    List<Item> findByEstadoAndPrioridade(String estado, Integer prioridade);
}
