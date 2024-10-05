package com.ecommerce.ecommerce.exceptions;

public class PasswordConfirmationNotEqualToPasswordException extends RuntimeException{

    public PasswordConfirmationNotEqualToPasswordException(){
        super("Password confirmation does not match password");
    }

}
