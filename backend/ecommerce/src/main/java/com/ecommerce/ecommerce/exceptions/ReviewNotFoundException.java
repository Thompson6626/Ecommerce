package com.ecommerce.ecommerce.exceptions;

public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException(Identifier identifier,Object variable){
        super(String.format(
            "Review not found using identifier '%s'; attempted to find '%s'.",
            identifier.toString(),
            variable.toString()
    ));
    }
}
