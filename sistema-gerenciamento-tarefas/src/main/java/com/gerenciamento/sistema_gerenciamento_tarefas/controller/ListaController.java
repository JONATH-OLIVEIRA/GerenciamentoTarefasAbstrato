package com.gerenciamento.sistema_gerenciamento_tarefas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciamento.sistema_gerenciamento_tarefas.comparator.ItemPrioridadeComparator;
import com.gerenciamento.sistema_gerenciamento_tarefas.dto.ListaUpdateDTO;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Gerenciamento de Listas", description = "Operações relacionadas ao gerenciamento de listas")
@RestController
@RequestMapping("/api/listas")
public class ListaController {

	@Autowired
	private ListaService listaService;

	// Busca todas as listas
	@GetMapping
	public ResponseEntity<List<Lista>> getAllListas() {
		List<Lista> listas = listaService.findAll();

		// Ordena os itens de cada lista pela prioridade
		ItemPrioridadeComparator comparator = new ItemPrioridadeComparator();
		listas.forEach(lista -> {
			if (lista.getItens() != null) {
				lista.getItens().sort(comparator);
			}
		});

		return ResponseEntity.ok(listas);
	}

	// Criação de uma nova lista
	@PostMapping
	public ResponseEntity<Lista> createLista(@Valid @RequestBody ListaUpdateDTO listaUpdateDTO) {
		Lista lista = new Lista();
		lista.setTitulo(listaUpdateDTO.getTitulo());
		Lista novaLista = listaService.create(lista);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
	}

	// Atualização de uma lista por ID
	// Atualização de uma lista por ID, usando o DTO
	@PutMapping("/{id}")
	public ResponseEntity<Lista> updateLista(@PathVariable Long id, @Valid @RequestBody ListaUpdateDTO listaDto) {
		// Recupera a lista existente no banco de dados
		Lista listaExistente = listaService.findById(id);

		if (listaExistente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se a lista não for encontrada
		}

		// Atualiza apenas os dados da lista (por exemplo, o título) a partir do DTO
		if (listaDto.getTitulo() != null) {
			listaExistente.setTitulo(listaDto.getTitulo());
		}

		// Salva a lista com o título atualizado, sem alterar os itens
		Lista listaAtualizadaBanco = listaService.update(listaExistente);

		return ResponseEntity.ok(listaAtualizadaBanco); // Retorna a lista atualizada
	}

	// Exclusão de uma lista por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
		listaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
