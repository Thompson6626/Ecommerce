package com.ecommerce.ecommerce.exceptions;

public class EmailAlreadyAssociatedWithAccount extends RuntimeException{
    public EmailAlreadyAssociatedWithAccount() {
        super("This email is already associated with an account");
    }
}
