package com.ecommerce.ecommerce.exceptions;


public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(Identifier identifier,Object variable){
        super("Role",identifier,variable);
    }
}

