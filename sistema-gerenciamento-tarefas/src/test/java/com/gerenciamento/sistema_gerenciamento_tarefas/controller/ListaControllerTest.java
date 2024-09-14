package com.gerenciamento.sistema_gerenciamento_tarefas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
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
    void testGetListasOuItens() throws Exception {
        Lista lista = new Lista();
        lista.setId(1L);
        lista.setTitulo("Lista de Teste");

        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item de Teste");
        lista.setItens(Collections.singletonList(item));

        Mockito.when(listaService.findById(Mockito.anyLong())).thenReturn(lista);

        mockMvc.perform(get("/api/listas/itens")
                .param("listaId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(item))));
    }

    @Test
    void testCreateLista() throws Exception {
        Lista lista = new Lista();
        lista.setTitulo("Minha Lista");

        Mockito.when(listaService.create(Mockito.any(Lista.class))).thenReturn(lista);

        mockMvc.perform(post("/api/listas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lista)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void testUpdateLista() throws Exception {
        Lista lista = new Lista();
        lista.setId(1L);
        lista.setTitulo("Lista Atualizada");

        Mockito.when(listaService.update(Mockito.any(Lista.class))).thenReturn(lista);

        mockMvc.perform(put("/api/listas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lista)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void testDeleteLista() throws Exception {
        mockMvc.perform(delete("/api/listas/1"))
                .andExpect(status().isNoContent());
    }

   
    @Test
    void testDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/listas/itens/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateItemEstado() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item com Estado Atualizado");
        item.setEstado(Estado.NEUTRO); // 

        Mockito.when(itemService.updateItemEstado(Mockito.anyLong(), Mockito.any(Estado.class))).thenReturn(item);

        mockMvc.perform(patch("/api/listas/1/estado")
                .param("novoEstado", "NEUTRO") 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(item)));
    }
}
