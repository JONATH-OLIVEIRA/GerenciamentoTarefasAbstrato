package com.gerenciamento.sistema_gerenciamento_tarefas.service;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;

import java.util.List;

public interface ItemService {
    Item findById(Long id);
    Item create(Item item);
    Item update(Item item);
    void delete(Long id);
    List<Item> findAll(String estado, Integer prioridade);
}