package com.ecommerce.ecommerce.exceptions;

import jakarta.persistence.EntityNotFoundException;

public abstract class NotFoundException extends EntityNotFoundException {
    protected NotFoundException(String entityName, Identifier identifier, Object variable) {
        super(String.format("%s not found using identifier '%s'; attempted to find '%s'.",
                entityName, identifier.toString(), variable.toString()));
    }
}

