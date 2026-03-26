package com.application.yms.exception;

/**
 * Top-level application exception used to wrap lower-level exceptions (e.g. infrastructure).
 * This is the exception type the global {@code @RestControllerAdvice} can translate into a clean HTTP error response.
 */
public class YmsBusinessException extends RuntimeException {

    public YmsBusinessException(String message) {
        super(message);
    }

    public YmsBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

