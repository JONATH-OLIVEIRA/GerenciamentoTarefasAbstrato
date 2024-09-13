package com.gerenciamento.sistema_gerenciamento_tarefas.service;

import java.util.List;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;

public interface ListaService {
    Lista findById(Long id);
    Lista create(Lista listaToCreate);
    Lista update(Lista listaToUpdate);
    void delete(Long id);
    List<Item> getItemsByListaId(Long listaId);
    List<Lista> findAll();
    boolean existsByTitulo(String titulo);
}




