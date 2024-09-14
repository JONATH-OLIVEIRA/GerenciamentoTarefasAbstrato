package com.gerenciamento.sistema_gerenciamento_tarefas.comparator;

import java.util.Comparator;
import com.gerenciamento.sistema_gerenciamento_tarefas.model.Item;
import com.gerenciamento.sistema_gerenciamento_tarefas.enums.Prioridade;

public class ItemPrioridadeComparator implements Comparator<Item> {

    @Override
    public int compare(Item i1, Item i2) {
        return getPrioridadeOrder(i1.getPrioridade()) - getPrioridadeOrder(i2.getPrioridade());
    }

    private int getPrioridadeOrder(Prioridade prioridade) {
        switch (prioridade) {
            case ALTA:
                return 1;
            case MEDIA:
                return 2;
            case BAIXA:
                return 3;
            default:
                return Integer.MAX_VALUE; // Para prioridades desconhecidas, coloca no final
        }
    }
}