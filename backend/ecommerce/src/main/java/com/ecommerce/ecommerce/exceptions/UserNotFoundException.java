package com.ecommerce.ecommerce.exceptions;


public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Identifier identifier,Object variable){
        super("User",identifier,variable);
    }
}
