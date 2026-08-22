package com.example.blogging_platform_api_migrated.exceptions;

import com.example.blogging_platform_api_migrated.dtos.ErrorDetails;

public class NoSuchPostException extends RuntimeException {

    public NoSuchPostException(String message) {
        super(message);
    }
}
