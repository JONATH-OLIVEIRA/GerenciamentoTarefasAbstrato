package com.gerenciamento.sistema_gerenciamento_tarefas.service;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;

import java.util.List;

public interface ListaService {
    Lista findById(Long id);
    Lista create(Lista listaToCreate);
    Lista update(Lista listaToUpdate);
    void delete(Long id);
    List<Item> getItemsByListaId(Long listaId);
    List<Lista> findAll();
}




