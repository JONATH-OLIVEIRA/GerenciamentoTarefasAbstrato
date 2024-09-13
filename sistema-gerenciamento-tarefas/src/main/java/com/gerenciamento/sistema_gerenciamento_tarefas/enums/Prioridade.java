package com.gerenciamento.sistema_gerenciamento_tarefas.enums;


public enum Prioridade {
    ALTA(1), MEDIA(2), BAIXA(3);

    private final int ordem;

    Prioridade(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}