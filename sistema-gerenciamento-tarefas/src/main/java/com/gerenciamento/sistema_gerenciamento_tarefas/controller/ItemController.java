package com.gerenciamento.sistema_gerenciamento_tarefas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gerenciamento.sistema_gerenciamento_tarefas.comparator.ItemPrioridadeComparator;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ItemService;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Gerenciamento de Itens", description = "Operações relacionadas ao gerenciamento de itens")
@RestController
@RequestMapping("/api/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<?> getItens(@RequestParam(required = false) Long listaId,
                                      @RequestParam(required = false) Estado estado,
                                      @RequestParam(required = false) Prioridade prioridade) {

        List<Item> itens;

        if (listaId != null) {
            Lista lista = listaService.findById(listaId);
            if (lista == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada.");
            }
            itens = lista.getItens();
        } else {
            itens = itemService.findAll(estado, prioridade);
        }

        if (estado != null) {
            itens = itens.stream().filter(item -> item.getEstado().equals(estado)).toList();
        }
        if (prioridade != null) {
            itens = itens.stream().filter(item -> item.getPrioridade().equals(prioridade)).toList();
        }

        itens.sort(new ItemPrioridadeComparator());
        return ResponseEntity.ok(itens);
    }

    @PostMapping("/{listaId}")
    public ResponseEntity<Item> addItemToLista(@PathVariable Long listaId, @Valid @RequestBody Item item) {
        Lista lista = listaService.findById(listaId);
        if (lista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        item.setLista(lista);
        Item novoItem = itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @Valid @RequestBody Item item) {
        Item existingItem = itemService.findById(itemId);
        if (existingItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        item.setLista(existingItem.getLista());
        Item updatedItem = itemService.update(item);
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Item> updateItemEstado(@PathVariable Long id, @RequestParam Estado novoEstado) {
        Item item = itemService.updateItemEstado(id, novoEstado);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        System.out.println("Tentando excluir o item com ID: " + id);
        Item item = itemService.findById(id);
        if (item == null) {
            System.out.println("Item não encontrado para ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            itemService.delete(id);
            System.out.println("Item excluído com sucesso, ID: " + id);
            return ResponseEntity.noContent().build();
        }
    }
}
