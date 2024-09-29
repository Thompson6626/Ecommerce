package com.ecommerce.ecommerce.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(Identifier identifier,Object variable){
        super(
            String.format(
                "Category not found using identifier '%s'; attempted to find '%s'.",
                identifier.toString(),
                variable.toString()
            )
        );
    }
}
