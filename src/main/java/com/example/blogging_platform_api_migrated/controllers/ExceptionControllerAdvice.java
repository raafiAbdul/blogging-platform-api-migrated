package com.example.blogging_platform_api_migrated.controllers;

import com.example.blogging_platform_api_migrated.dtos.ErrorDetails;
import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException e) {
        Throwable cause = e.getRootCause();
        ErrorDetails ed = new ErrorDetails(e.getMessage());
        if(cause instanceof ConstraintViolationException cve)  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ConstraintViolationExceptionHelperMethod(cve));
        }
        if(cause instanceof NoSuchPostException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ed.getDetails());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ed.getDetails());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ConstraintViolationExceptionHelperMethod(e));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchPostException.class)
    public ResponseEntity<?> handleNoSuchPostException(NoSuchPostException e) {
        ErrorDetails ed = new ErrorDetails(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ed.getDetails());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        ErrorDetails ed = new ErrorDetails(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ed.getDetails());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        ErrorDetails ed = new ErrorDetails(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ed.getDetails());
    }

    private Map<String, String> ConstraintViolationExceptionHelperMethod(
            ConstraintViolationException cve
    ) {
        Map<String, String> violations = new HashMap<>();
        cve.getConstraintViolations().forEach(violation -> {
            violations.put(violation.getPropertyPath().toString(), violation.getMessage());
        });
        return violations;
    }

}
