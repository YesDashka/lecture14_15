package com.example.lecture9_10_11.exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue){
        super(String.format("%s not found with %s: %d", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String foreignFieldName){
        super(String.format("%s not found with specified %s or %s ", resourceName, fieldName, foreignFieldName));
    }
}
