package com.ecommerce.ecommerce.exceptions;


public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException(Identifier identifier,Object variable){
        super("Review",identifier,variable);
    }
}
