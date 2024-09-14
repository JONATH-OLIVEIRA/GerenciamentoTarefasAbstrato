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
import com.gerenciamento.sistema_gerenciamento_tarefas.comparator.ItemPrioridadeComparator;
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
	private ItemService itemService;

	// Busca todas as listas
	@GetMapping
	public ResponseEntity<List<Lista>> getAllListas() {
	    List<Lista> listas = listaService.findAll();

	    // Ordena os itens de cada lista pela prioridade
	    ItemPrioridadeComparator comparator = new ItemPrioridadeComparator();
	    for (Lista lista : listas) {
	        if (lista.getItens() != null) {
	            lista.getItens().sort(comparator);
	        }
	    }

	    return ResponseEntity.ok(listas);
	}

	// Busca todas as listas ou itens com filtros por ID de lista, estado ou
	// prioridade
	@GetMapping("/itens")
	public ResponseEntity<?> getListasOuItens(@RequestParam(required = false) Long listaId,
			@RequestParam(required = false) Estado estado, @RequestParam(required = false) Prioridade prioridade) {

		if (listaId != null) {
			Lista lista = listaService.findById(listaId);
			if (lista != null) {
				List<Item> itensFiltrados = lista.getItens(); // Pega os itens da lista

				// Aplica os filtros de estado e prioridade se forem fornecidos
				if (estado != null) {
					itensFiltrados = itensFiltrados.stream().filter(item -> item.getEstado().equals(estado)).toList();
				}
				if (prioridade != null) {
					itensFiltrados = itensFiltrados.stream().filter(item -> item.getPrioridade().equals(prioridade))
							.toList();
				}

				// Ordena os itens por prioridade
				itensFiltrados.sort(new ItemPrioridadeComparator());

				return ResponseEntity.ok(itensFiltrados);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada.");
			}
		} else {
			if (estado != null || prioridade != null) {
				List<Item> itens = itemService.findAll(estado, prioridade);

				// Ordena os itens por prioridade
				itens.sort(new ItemPrioridadeComparator());

				return ResponseEntity.ok(itens);
			} else {
				List<Lista> listas = listaService.findAll();

				// Ordena os itens de cada lista por prioridade
				listas.forEach(lista -> lista.getItens().sort(new ItemPrioridadeComparator()));

				return ResponseEntity.ok(listas);
			}
		}
	}

	// Criação de uma nova lista
	@PostMapping
	public ResponseEntity<Lista> createLista(@Valid @RequestBody Lista lista) {
		Lista novaLista = listaService.create(lista);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
	}

	// Atualização de uma lista por ID
	@PutMapping("/{id}")
	public ResponseEntity<Lista> updateLista(@PathVariable Long id, @Valid @RequestBody Lista lista) {
		lista.setId(id);
		Lista updatedLista = listaService.update(lista);
		return updatedLista != null ? ResponseEntity.ok(updatedLista)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// Exclusão de uma lista por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
		listaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Adiciona um item a uma lista específica
	@PostMapping("/{listaId}/itens")
	public ResponseEntity<Item> addItemToLista(@PathVariable Long listaId, @Valid @RequestBody Item item) {
		Lista lista = listaService.findById(listaId);
		if (lista == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		item.setLista(lista);
		Item novoItem = itemService.create(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
	}

	// Atualização de um item específico por ID
	@PutMapping("/itens/{itemId}")
	public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @Valid @RequestBody Item item) {
		Item existingItem = itemService.findById(itemId);
		return existingItem != null ? ResponseEntity.ok(itemService.update(item))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// Exclusão de um item específico por ID
	@DeleteMapping("/itens/{itemId}")
	public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
		itemService.delete(itemId);
		return ResponseEntity.noContent().build();
	}

	// Atualização do estado de um item
	@PatchMapping("/{id}/estado")
	public ResponseEntity<Item> updateItemEstado(@PathVariable Long id, @RequestParam Estado novoEstado) {
		Item item = itemService.updateItemEstado(id, novoEstado);
		return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
	}
}
