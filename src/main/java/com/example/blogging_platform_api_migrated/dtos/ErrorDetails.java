package com.example.blogging_platform_api_migrated.dtos;

public class ErrorDetails {
    private final String details;

    public ErrorDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
