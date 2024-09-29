package com.ecommerce.ecommerce.product;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.product.dto.ProductDisplay;
import com.ecommerce.ecommerce.product.dto.ProductShowcase;

@Service
public class ProductMapper {


    public ProductShowcase toShowCase(Product product){
        return ProductShowcase.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .rating(product.getRating())
                .build();
    }
    public ProductDisplay toDisplay(Product product){
        return ProductDisplay.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stock(product.getStock())
                .rating(product.getRating())
                .category(product.getCategory().getName())
                .build();
    }





}
