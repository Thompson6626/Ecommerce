package com.ecommerce.ecommerce.exceptions;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Identifier identifier, Object variable) {
        super("Order", identifier, variable);
    }
}