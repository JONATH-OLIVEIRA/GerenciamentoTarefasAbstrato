package com.gerenciamento.sistema_gerenciamento_tarefas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gerenciamento.sistema_gerenciamento_tarefas.globalExceptionHandler.exceptions.ListaExistenteException;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.repository.ItemRepository;
import com.gerenciamento.sistema_gerenciamento_tarefas.repository.ListaRepository;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.implementacao.ListaServiceImpl;

public class ListaServiceTest {

    @Mock
    private ListaRepository listaRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ListaServiceImpl listaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Lista lista = new Lista();
        lista.setId(1L);
        when(listaRepository.findById(anyLong())).thenReturn(Optional.of(lista));

        Lista result = listaService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testCreate() {
        Lista lista = new Lista();
        lista.setTitulo("Minha Lista");

        when(listaRepository.findByTitulo(anyString())).thenReturn(Optional.empty());
        when(listaRepository.save(any(Lista.class))).thenReturn(lista);

        Lista result = listaService.create(lista);

        assertNotNull(result);
        assertEquals("Minha Lista", result.getTitulo());
    }

    @Test
    void testCreateThrowsExceptionIfExists() {
        Lista lista = new Lista();
        lista.setTitulo("Minha Lista");

        when(listaRepository.findByTitulo(anyString())).thenReturn(Optional.of(lista));

        ListaExistenteException thrown = assertThrows(ListaExistenteException.class, () -> {
            listaService.create(lista);
        });

        assertEquals("Já existe uma lista com o título: Minha Lista", thrown.getMessage());
    }

    @Test
    void testUpdate() {
        Lista lista = new Lista();
        lista.setId(1L);
        lista.setTitulo("Lista Atualizada");

        when(listaRepository.existsById(anyLong())).thenReturn(true);
        when(listaRepository.save(any(Lista.class))).thenReturn(lista);

        Lista result = listaService.update(lista);

        assertNotNull(result);
        assertEquals("Lista Atualizada", result.getTitulo());
    }

    @Test
    void testUpdateReturnsNullIfNotExists() {
        Lista lista = new Lista();
        lista.setId(1L);

        when(listaRepository.existsById(anyLong())).thenReturn(false);

        Lista result = listaService.update(lista);

        assertNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(listaRepository).deleteById(anyLong());

        listaService.delete(1L);

        verify(listaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetItemsByListaId() {
        Item item = new Item();
        when(itemRepository.findByListaId(anyLong())).thenReturn(Collections.singletonList(item));

        List<Item> result = listaService.getItemsByListaId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAll() {
        Lista lista = new Lista();
        when(listaRepository.findAll()).thenReturn(Collections.singletonList(lista));

        List<Lista> result = listaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testExistsByTitulo() {
        when(listaRepository.findByTitulo(anyString())).thenReturn(Optional.of(new Lista()));

        boolean result = listaService.existsByTitulo("Título");

        assertTrue(result);
    }

    @Test
    void testExistsByTituloReturnsFalse() {
        when(listaRepository.findByTitulo(anyString())).thenReturn(Optional.empty());

        boolean result = listaService.existsByTitulo("Título");

        assertFalse(result);
    }
}
