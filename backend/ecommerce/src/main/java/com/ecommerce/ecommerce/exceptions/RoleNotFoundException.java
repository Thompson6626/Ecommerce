package com.ecommerce.ecommerce.exceptions;


public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String name) {
        super(String.format("Role with name %s not found", name));
    }
}

