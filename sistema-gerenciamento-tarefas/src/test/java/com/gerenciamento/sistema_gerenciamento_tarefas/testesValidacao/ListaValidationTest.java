package com.gerenciamento.sistema_gerenciamento_tarefas.testesValidacao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gerenciamento.sistema_gerenciamento_tarefas.model.Lista;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ListaValidationTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidacaoTitulo() {
        Lista lista = new Lista(""); // Dados inválidos
        Set<ConstraintViolation<Lista>> violations = validator.validate(lista);
        // Verifica se há alguma violação (deveria haver)
        assertFalse(violations.isEmpty(), "A lista deve ter validações de erro.");
    }

    @Test
    public void testTituloTamanhoMinimo() {
        Lista lista = new Lista("AB"); // Título muito curto
        Set<ConstraintViolation<Lista>> violations = validator.validate(lista);
        
        // Verifica se há pelo menos uma violação
        assertFalse(violations.isEmpty(), "A lista deve ter violações de validação.");
        
        // Verifica se há uma violação com a mensagem esperada
        assertTrue(violations.stream()
                             .anyMatch(violation -> violation.getMessage().contains("O título deve ter entre 3 e 100 caracteres.")),
                   "A lista deve gerar uma violação para título muito curto.");
    }
}
