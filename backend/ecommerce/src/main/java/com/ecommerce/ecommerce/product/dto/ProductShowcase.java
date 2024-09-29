package com.ecommerce.ecommerce.product.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShowcase {

    private Integer id;
    private String name;
    private Double price;
    private String imageUrl;
    private Double rating;
}
