package com.gerenciamento.sistema_gerenciamento_tarefas.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Estado;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "tb_item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição não pode estar vazia.")
    @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres.")
    private String descricao;

    @NotNull(message = "O estado não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @NotNull(message = "A prioridade não pode ser nula.")
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

    @Override
    public String toString() {
        return "Item [id=" + id + ", descricao=" + descricao + ", estado=" + estado + ", prioridade=" + prioridade + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return Objects.equals(id, other.id);
    }
}
