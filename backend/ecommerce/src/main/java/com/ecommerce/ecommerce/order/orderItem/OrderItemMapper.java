package com.ecommerce.ecommerce.order.orderItem;

import com.ecommerce.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemMapper {

    private final ProductRepository productRepository;
    public OrderItem toOrderItem(OrderItemRequest request){
        return OrderItem.builder()
                .product(productRepository.getReferenceById(request.productId()))
                .quantity(request.quantity())
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem item){
        return OrderItemResponse.builder()
                .productName(item.getProduct().getName())
                .individualPrice(item.getProduct().getPrice())
                .imageUrl(item.getProduct().getImageUrl())
                .batchPrice(item.getBatchPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
