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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;  // Certifique-se de que você tem a enum Prioridade
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ItemService;
import com.gerenciamento.sistema_gerenciamento_tarefas.service.ListaService;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ListaService listaService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetItens() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item de Teste");
        item.setEstado(Estado.NEUTRO);  // Defina um valor padrão
        item.setPrioridade(Prioridade.MEDIA);  // Defina um valor padrão

        Lista lista = new Lista();
        lista.setId(1L);
        lista.setItens(Collections.singletonList(item));

        Mockito.when(listaService.findById(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/itens")
                .param("listaId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(item))));
    }

    @Test
    void testAddItemToLista() throws Exception {
        Item item = new Item();
        item.setDescricao("Item de Teste");
        item.setEstado(Estado.NEUTRO);  // Defina um valor padrão
        item.setPrioridade(Prioridade.MEDIA);  // Defina um valor padrão

        Lista lista = new Lista();
        lista.setId(1L);

        Mockito.when(listaService.findById(1L)).thenReturn(lista);
        Mockito.when(itemService.create(Mockito.any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/api/itens/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(item)));
    }

    @Test
    void testUpdateItem() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item Atualizado");
        item.setEstado(Estado.NEUTRO);  // Defina um valor padrão
        item.setPrioridade(Prioridade.MEDIA);  // Defina um valor padrão

        Mockito.when(itemService.findById(1L)).thenReturn(item);
        Mockito.when(itemService.update(Mockito.any(Item.class))).thenReturn(item);

        mockMvc.perform(put("/api/itens/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(item)));
    }

    @Test
    void testUpdateItemEstado() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item com Estado Atualizado");
        item.setEstado(Estado.NEUTRO);  // Defina um valor padrão
        item.setPrioridade(Prioridade.MEDIA);  // Defina um valor padrão

        Mockito.when(itemService.updateItemEstado(1L, Estado.NEUTRO)).thenReturn(item);

        mockMvc.perform(patch("/api/itens/1/estado")
                .param("novoEstado", "NEUTRO")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(item)));
    }

    @Test
    void testDeleteItem() throws Exception {
        // Cria um item de teste
        Item item = new Item();
        item.setId(1L);
        item.setDescricao("Item de Teste");

        // Configura o mock para retornar o item quando findById for chamado
        Mockito.when(itemService.findById(1L)).thenReturn(item);
        
        // Configura o mock para não fazer nada quando delete for chamado
        Mockito.doNothing().when(itemService).delete(1L);

        // Executa a requisição DELETE e verifica se o status da resposta é 204 No Content
        mockMvc.perform(delete("/api/itens/1"))
                .andExpect(status().isNoContent());
    }
}
