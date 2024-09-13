package com.gerenciamento.sistema_gerenciamento_tarefas.globalExceptionHandler.exceptions;

public class ListaExistenteException extends RuntimeException {
    public ListaExistenteException(String message) {
        super(message);
    }
}