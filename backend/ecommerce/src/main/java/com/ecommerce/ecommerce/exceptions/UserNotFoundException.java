package com.ecommerce.ecommerce.exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final String[] USER_SEARCH_IDENTIFIER = {
            "ID",
            "EMAIL"
    };

    public UserNotFoundException(int identifierIndx,Object variable) {
        super(
            String.format(
                "User not found using identifier '%s'; attempted to find '%s'.",
                USER_SEARCH_IDENTIFIER[identifierIndx],
                variable.toString()
        ));
    }
}
