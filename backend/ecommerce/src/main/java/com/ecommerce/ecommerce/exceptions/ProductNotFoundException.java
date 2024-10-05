package com.ecommerce.ecommerce.exceptions;


public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Identifier identifier, Object variable) {
        super("Product", identifier, variable);
    }
}
