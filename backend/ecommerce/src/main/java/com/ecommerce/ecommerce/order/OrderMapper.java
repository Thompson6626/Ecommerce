package com.ecommerce.ecommerce.order;

import com.ecommerce.ecommerce.order.dto.OrderRequest;
import com.ecommerce.ecommerce.order.dto.OrderResponse;
import com.ecommerce.ecommerce.order.orderItem.OrderItemMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    public Order toOrder(OrderRequest request){
        return Order.builder()
                .orderItems(
                        request.orderItems().stream()
                                .map(orderItemMapper::toOrderItem)
                                .collect(Collectors.toList())
                )
                .status(OrderStatus.PENDING)
                .build();
    }

    public OrderResponse toResponse(Order order){
        return OrderResponse.builder()
                .productsPurchased(
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toOrderItemResponse)
                                .collect(Collectors.toList())
                )
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getStatus().name())
                .orderDate(order.getOrderDate())
                .build();
    }

}
