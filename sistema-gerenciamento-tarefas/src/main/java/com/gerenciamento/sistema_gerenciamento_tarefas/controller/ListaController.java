package com.gerenciamento.sistema_gerenciamento_tarefas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ItemService;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

	@Autowired
	private ListaService listaService;

	@Autowired
	private ItemService itemService; // Injetando o ItemService

	@GetMapping
	public ResponseEntity<List<Lista>> getAllListas() {
		List<Lista> listas = listaService.findAll();
		return ResponseEntity.ok(listas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Lista> getListaById(@PathVariable Long id) {
		Lista lista = listaService.findById(id);
		if (lista != null) {
			return ResponseEntity.ok(lista);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping
	public ResponseEntity<Lista> createLista(@RequestBody Lista lista) {
		Lista novaLista = new Lista(lista.getTitulo());
		Lista listaSalva = listaService.create(novaLista);
		return ResponseEntity.status(HttpStatus.CREATED).body(listaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Lista> updateLista(@PathVariable Long id, @Valid @RequestBody Lista lista) {
		lista.setId(id);
		Lista updatedLista = listaService.update(lista);
		if (updatedLista != null) {
			return ResponseEntity.ok(updatedLista);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
		listaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{listaId}/itens")
	public ResponseEntity<Item> addItemToLista(@PathVariable Long listaId, @Valid @RequestBody Item item) {
		Lista lista = listaService.findById(listaId);
		if (lista == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		item.setLista(lista);
		Item novoItem = itemService.create(item); // Usando o ItemService para criar o item
		return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
	}

	@PutMapping("/itens/{itemId}")
	public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @Valid @RequestBody Item item) {
		Item existingItem = itemService.findById(itemId);
		if (existingItem != null) {
			item.setId(itemId);
			Item updatedItem = itemService.update(item);
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/itens/{itemId}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
		itemService.delete(itemId);
		return ResponseEntity.noContent().build();
	}

	 @GetMapping("/itens")
	    public ResponseEntity<List<Item>> getAllItens(
	            @RequestParam(required = false) Estado estado,
	            @RequestParam(required = false) Prioridade prioridade) {
	        
	        // Debug: Imprima os parâmetros recebidos
	        System.out.println("Estado: " + estado);
	        System.out.println("Prioridade: " + prioridade);

	        List<Item> itens = itemService.findAll(estado, prioridade);
	        return ResponseEntity.ok(itens);
	    }
	 @PatchMapping("/{id}/estado")
	    public ResponseEntity<Item> updateItemEstado(
	            @PathVariable Long id,
	            @RequestParam Estado novoEstado) {
	        Item item = itemService.updateItemEstado(id, novoEstado);
	        if (item != null) {
	            return ResponseEntity.ok(item);
	        }
	        return ResponseEntity.notFound().build();
	    }
}