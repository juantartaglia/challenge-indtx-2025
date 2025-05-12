package com.jmt.challengeindtx.infrastructure.web.configuration;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    private String message;
    private List<String> errors;

    public ApiError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String message, String error) {
        this.message = message;
        errors = Arrays.asList(error);
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
