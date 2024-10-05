package com.ecommerce.ecommerce.exceptions;

public class TokenHasExpiredException extends RuntimeException{

    private static final String BASE_MESSAGE = "Token has expired. ";
    public TokenHasExpiredException() {
        super(BASE_MESSAGE);
    }
    public TokenHasExpiredException(String message) {
        super(BASE_MESSAGE + message);
    }
}
