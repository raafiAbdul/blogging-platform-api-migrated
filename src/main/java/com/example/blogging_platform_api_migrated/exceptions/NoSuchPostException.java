package com.example.blogging_platform_api_migrated.exceptions;

import com.example.blogging_platform_api_migrated.dtos.NoSuchPostExceptionDetails;

public class NoSuchPostException extends RuntimeException {

    private NoSuchPostExceptionDetails details;

    public NoSuchPostException(String message) {
        details = new NoSuchPostExceptionDetails(message);
        super(message);
    }

    public NoSuchPostExceptionDetails getDetails() {
        return details;
    }
}
