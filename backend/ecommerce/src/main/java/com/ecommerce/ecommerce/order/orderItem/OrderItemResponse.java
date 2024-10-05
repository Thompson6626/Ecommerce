package com.ecommerce.ecommerce.order.orderItem;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
        String productName,
        int quantity,
        BigDecimal individualPrice,
        BigDecimal batchPrice,
        String imageUrl
) {

}
