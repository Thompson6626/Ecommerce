package com.ecommerce.ecommerce.exceptions;


public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Identifier identifier, Object variable) {
        super("Category", identifier, variable);
    }
}
