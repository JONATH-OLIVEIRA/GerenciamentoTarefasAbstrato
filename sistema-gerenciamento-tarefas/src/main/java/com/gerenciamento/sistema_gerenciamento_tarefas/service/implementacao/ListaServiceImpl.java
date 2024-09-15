package com.gerenciamento.sistema_gerenciamento_tarefas.service.implementacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento.sistema_gerenciamento_tarefas.globalExceptionHandler.exceptions.ListaExistenteException;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.repository.ItemRepository;
import com.gerenciamento.sistema_gerenciamento_tarefas.repository.ListaRepository;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

@Service
public class ListaServiceImpl implements ListaService {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Lista findById(Long id) {
        return listaRepository.findById(id).orElse(null);
    }

    @Override
    public Lista create(Lista listaToCreate) {
        if (existsByTitulo(listaToCreate.getTitulo())) {
            throw new ListaExistenteException("Já existe uma lista com o título: " + listaToCreate.getTitulo());
        }
        return listaRepository.save(listaToCreate);
    }

    @Override
    public Lista update(Lista listaToUpdate) {
        if (listaRepository.existsById(listaToUpdate.getId())) {
            return listaRepository.save(listaToUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        listaRepository.deleteById(id);
    }

    @Override
    public List<Item> getItemsByListaId(Long listaId) {
        return itemRepository.findByListaId(listaId);
    }

    @Override
    public List<Lista> findAll() {
        return listaRepository.findAll();  // Busca todas as listas
    }

    @Override
    public boolean existsByTitulo(String titulo) {
        return listaRepository.findByTitulo(titulo).isPresent();
    }

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
  }
