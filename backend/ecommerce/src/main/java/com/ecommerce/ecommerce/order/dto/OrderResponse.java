package com.ecommerce.ecommerce.order.dto;

import com.ecommerce.ecommerce.order.orderItem.OrderItemResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record OrderResponse(
        String orderStatus,
        List<OrderItemResponse> productsPurchased,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate orderDate,
        BigDecimal totalPrice
) {
}
