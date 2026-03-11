package com.github.cidarosa.ms.produto.exceptions.handler;

import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.exceptions.dto.CustomErrorDTO;
import com.github.cidarosa.ms.produto.exceptions.dto.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<CustomErrorDTO> handlerControlerNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(),status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());

        for (FieldError fieldError: e.getBindingResult().getFieldErrors()){
err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorDTO> handleHttpNotReadable (HttpMessageNotReadableException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Requisição inválida (JSON malformado)", request.getRequestURI());
    return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorDTO> handleTypeMismatch (MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Requisição inválida (parametro ou formato errado)", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorDTO> handleGenericException(Exception e, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Erro interno inesperado", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }*/
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomErrorDTO> handleDatabaseException(DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(),status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);

    }
}
