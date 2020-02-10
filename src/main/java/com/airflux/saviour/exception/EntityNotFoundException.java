package com.airflux.saviour.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType, String entityId) {
        super(String.format("%s with id %s not found", entityType, entityId));
    }

    public EntityNotFoundException(String entityType, String field, String fieldValue) {
        super(String.format("%s with %s = %s not found", entityType, field, fieldValue));
    }
}
