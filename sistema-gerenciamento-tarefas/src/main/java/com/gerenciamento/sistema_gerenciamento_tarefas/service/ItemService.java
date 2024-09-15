package com.gerenciamento.sistema_gerenciamento_tarefas.service;

import java.util.List;

import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;

public interface ItemService {
    Item findById(Long id);
    Item create(Item item);
    Item update(Item item);
    void delete(Long id); // Adicionado
    List<Item> findAll(Estado estado, Prioridade prioridade);
    List<Item> findAll();
    List<Item> findByListaId(Long listaId);
    Item updateItemEstado(Long id, Estado novoEstado);
}
