package com.ecommerce.ecommerce.exceptions;

public class InvalidTokenException extends RuntimeException{


    public InvalidTokenException(){
        super("Invalid token");
    }

}
