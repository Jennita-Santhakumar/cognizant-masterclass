package com.cognizant.rest.exception;

import java.time.Instant;
import java.util.List;

/**
 * Consistent error shape returned for every failure, instead of a raw
 * stack trace or an inconsistent, endpoint-specific error body.
 */
public class ApiError {
    private final Instant timestamp = Instant.now();
    private final int status;
    private final String error;
    private final String message;
    private final List<String> details;

    public ApiError(int status, String error, String message, List<String> details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
