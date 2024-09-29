package com.ecommerce.ecommerce.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Identifier identifier,Object variable) {
        super(
            String.format(
                "Product not found using identifier '%s'; attempted to find '%s'.",
                identifier.toString(),
                variable.toString()
        ));
    }
}
