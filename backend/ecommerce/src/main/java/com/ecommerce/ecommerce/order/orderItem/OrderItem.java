package com.ecommerce.ecommerce.order.orderItem;

import com.ecommerce.ecommerce.order.Order;
import com.ecommerce.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;

    public BigDecimal getBatchPrice(){
        if (product == null || product.getPrice() == null || quantity == null){
            return BigDecimal.ZERO;
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}

