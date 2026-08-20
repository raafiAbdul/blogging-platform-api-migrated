package com.example.blogging_platform_api_migrated.dtos;

public class NoSuchPostExceptionDetails {
    private final String details;

    public NoSuchPostExceptionDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
