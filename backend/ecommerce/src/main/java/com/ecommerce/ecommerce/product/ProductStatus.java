package com.ecommerce.ecommerce.product;


public enum ProductStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public static ProductStatus from(String status){
        String st = status.trim().toUpperCase();
        return ProductStatus.valueOf(st);
    }

}
