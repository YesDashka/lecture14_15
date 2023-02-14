package com.example.lecture9_10_11.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e){
        Map<String, String> violations = e.getConstraintViolations()
                .stream()
                .map(error -> {
                    String fieldName = error.getPropertyPath().toString();
                    String message = error.getMessage();
                    return Map.entry(fieldName, message);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new ResponseEntity<>(violations, HttpStatus.BAD_REQUEST);
    }
}
