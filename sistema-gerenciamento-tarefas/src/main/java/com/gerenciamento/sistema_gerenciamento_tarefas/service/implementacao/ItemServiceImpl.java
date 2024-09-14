package com.gerenciamento.sistema_gerenciamento_tarefas.service.implementacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.repository.ItemRepository;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Item findById(Long id) {
		return itemRepository.findById(id).orElse(null);
	}

	@Override
	public Item create(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Item update(Item item) {
		if (itemRepository.existsById(item.getId())) {
			return itemRepository.save(item);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		itemRepository.deleteById(id);
	}

	@Override
	public List<Item> findAll(Estado estado, Prioridade prioridade) {
		if (estado != null && prioridade != null) {
			return itemRepository.findByEstadoAndPrioridade(estado, prioridade);
		} else if (estado != null) {
			return itemRepository.findByEstado(estado);
		} else if (prioridade != null) {
			return itemRepository.findByPrioridade(prioridade);
		} else {
			return itemRepository.findAll(); // Retorna todos os itens se nenhum filtro é aplicado
		}
	}

	public List<Item> findByListaId(Long listaId) {
		// Retorna a lista de itens baseado no ID da lista
		return itemRepository.findByListaId(listaId);
	}

	@Override
	public Item updateItemEstado(Long id, Estado novoEstado) {
		Item item = findById(id);
		if (item != null) {
			item.setEstado(novoEstado);
			return update(item);
		}
		return null;
	}

	 @Override
	    public List<Item> findAll() {
	        return itemRepository.findAll();
	    }
	 
	 public List<Item> findAllByListaId(Long listaId) {
			// Retorna a lista de itens baseado no ID da lista
			return itemRepository.findByListaId(listaId);
		}

}
