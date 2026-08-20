package com.example.blogging_platform_api_migrated.controllers;

import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> violations = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            violations.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violations.toString());
    }

    @ExceptionHandler(NoSuchPostException.class)
    public ResponseEntity<String> handleException(NoSuchPostException ex) {
        return  ResponseEntity.notFound().build();
    }

}
