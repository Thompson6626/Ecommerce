package com.ecommerce.ecommerce.order.orderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull(message = "Provide a valid id")
        @Positive(message = "Provide a valid id")
        Integer productId,
        @NotNull(message = "Provide a valid quantity")
        @Positive(message = "Provide a valid quantity")
        Integer quantity
) {
}
