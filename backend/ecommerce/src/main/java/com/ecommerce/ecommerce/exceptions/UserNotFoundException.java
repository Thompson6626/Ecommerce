package com.ecommerce.ecommerce.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Identifier identifier, Object variable){
        super(
            String.format(
                "User not found using identifier '%s'; attempted to find '%s'.",
                identifier.toString(),
                variable.toString()
            )
        );
    }
}
