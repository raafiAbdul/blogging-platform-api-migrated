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

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException cve =  (ConstraintViolationException) e;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ConstraintViolationExceptionHelperMethod(cve));
        }

        if(e instanceof TransactionSystemException) {
            Throwable cause = ((TransactionSystemException)e).getRootCause();
            if(cause instanceof ConstraintViolationException cve)  {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ConstraintViolationExceptionHelperMethod(cve));
            }
        }

        if(e instanceof NoSuchPostException n) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(n.getDetails());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
