package com.ecommerce.ecommerce.exceptions;

public class UnauthorizedModificationException extends RuntimeException{

    public UnauthorizedModificationException() {
        super("You can only modify what belongs to you.");
    }

    public UnauthorizedModificationException(String message) {
        super(message);
    }
}
