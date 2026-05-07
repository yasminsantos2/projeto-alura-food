package br.com.alurafood.pagamentos.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(org.springframework.data.mapping.PropertyReferenceException.class)
    public ResponseEntity tratarErro400(org.springframework.data.mapping.PropertyReferenceException ex) {
        return ResponseEntity.badRequest().body("Campo de ordenação inválido: " + ex.getPropertyName());
    }
}
