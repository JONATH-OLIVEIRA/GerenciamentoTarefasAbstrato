package com.gerenciamento.sistema_gerenciamento_tarefas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.sistema_gerenciamento_tarefas.dto.ListaUpdateDTO;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ItemService;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

@WebMvcTest(ListaController.class)
public class ListaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListaService listaService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllListas() throws Exception {
        Lista lista = new Lista();
        lista.setId(1L);
        lista.setTitulo("Lista de Teste");

        Mockito.when(listaService.findAll()).thenReturn(Collections.singletonList(lista));

        mockMvc.perform(get("/api/listas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(lista))));
    }

    @Test
    void testCreateLista() throws Exception {
        ListaUpdateDTO listaUpdateDTO = new ListaUpdateDTO();
        listaUpdateDTO.setTitulo("Minha Lista");

        Lista lista = new Lista();
        lista.setId(1L);
        lista.setTitulo("Minha Lista");

        Mockito.when(listaService.create(Mockito.any(Lista.class))).thenReturn(lista);

        mockMvc.perform(post("/api/listas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listaUpdateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void testUpdateLista() throws Exception {
        ListaUpdateDTO listaUpdateDTO = new ListaUpdateDTO();
        listaUpdateDTO.setTitulo("Lista Atualizada");

        Lista listaExistente = new Lista();
        listaExistente.setId(1L);
        listaExistente.setTitulo("Lista Antiga");

        Lista listaAtualizada = new Lista();
        listaAtualizada.setId(1L);
        listaAtualizada.setTitulo("Lista Atualizada");

        Mockito.when(listaService.findById(1L)).thenReturn(listaExistente);
        Mockito.when(listaService.update(Mockito.any(Lista.class))).thenReturn(listaAtualizada);

        mockMvc.perform(put("/api/listas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(listaUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(listaAtualizada)));
    }

      

}

