package com.example.backend_alquiler_canchas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(
                false, 
                ex.getMessage(),
                null // Si no hay datos adicionales, pasamos null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<GlobalResponse<String>> handleNullPointerException(NullPointerException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(
                false, 
                "Error interno del servidor, por favor contacte al administrador.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> handleGenericException(Exception ex) {
        GlobalResponse<String> response = new GlobalResponse<>(
                false,
                "Ha ocurrido un error inesperado.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(
                false, 
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
