package com.ecommerce.ecommerce.product;

import com.ecommerce.ecommerce.product.dto.ProductRequest;
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

    public Product toProduct(ProductRequest request){
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .imageUrl(request.imageUrl())
                .description(request.description())
                .status(ProductStatus.PENDING)
                .build();
    }




}
