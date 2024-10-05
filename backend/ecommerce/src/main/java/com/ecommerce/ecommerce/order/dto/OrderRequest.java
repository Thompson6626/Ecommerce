package com.ecommerce.ecommerce.order.dto;

import com.ecommerce.ecommerce.order.orderItem.OrderItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest (
    @Size(min = 1,message = "Must provide at least 1 product to order")
    List<@Valid OrderItemRequest> orderItems
){
}
