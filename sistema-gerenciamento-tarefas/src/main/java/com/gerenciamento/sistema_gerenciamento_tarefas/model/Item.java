package com.gerenciamento.sistema_gerenciamento_tarefas.model;

import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "tb_item")
public class Item implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @ManyToOne
    @JsonIgnore
    private Lista lista;

    // Construtores
    public Item() {}

    public Item(String descricao, Estado estado, Prioridade prioridade, Lista lista) {
        this.descricao = descricao;
        this.estado = estado;
        this.prioridade = prioridade;
        this.lista = lista;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }
}
