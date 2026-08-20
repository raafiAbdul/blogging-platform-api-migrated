package com.example.blogging_platform_api_migrated.controllers;

import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        Throwable rootCause = (e instanceof TransactionSystemException tse) ? tse.getRootCause() : e;

        if (rootCause instanceof ConstraintViolationException) {
            Map<String, String> violations = new HashMap<>();
            ConstraintViolationException ex = (ConstraintViolationException) rootCause;
            ex.getConstraintViolations().forEach(violation -> {
                violations.put(violation.getPropertyPath().toString(), violation.getMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violations);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
