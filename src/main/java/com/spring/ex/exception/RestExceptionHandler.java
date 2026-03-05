package com.spring.ex.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * RestExceptionHandler returns structured JSON error responses for REST controllers.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle HttpRequestMethodNotSupportedException and return HTTP 405 with JSON body.
     *
     * @param ex the exception thrown when request method is not supported by a handler
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now().toString());
        body.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put("error", "Method Not Allowed");
        body.put("message", ex.getMessage());
        String[] methods = ex.getSupportedMethods();
        if (methods != null && methods.length > 0) {
            body.put("supportedMethods", methods);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", String.join(",", methods != null ? methods : new String[]{}));
        return new ResponseEntity<>(body, headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Generic fallback handler for uncaught exceptions to avoid view resolution attempts.
     *
     * @param ex any uncaught exception
     * @return ResponseEntity with HTTP 500 and basic error info
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now().toString());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

