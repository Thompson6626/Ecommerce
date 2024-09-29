package com.ecommerce.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDisplay {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer stock; 
    private Double rating;
    private String category;
}
