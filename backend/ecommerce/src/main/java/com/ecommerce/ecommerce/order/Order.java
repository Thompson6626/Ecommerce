package com.ecommerce.ecommerce.order;

import com.ecommerce.ecommerce.order.orderItem.OrderItem;
import com.ecommerce.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    @Column(nullable = false , updatable = false)
    private LocalDate orderDate;
    private BigDecimal totalPrice;

    @Transient
    public BigDecimal getTotalPrice(){
        if (this.orderItems == null || this.orderItems.isEmpty()) {
            return BigDecimal.ZERO;
        }


        return this.orderItems.stream()
                .map(OrderItem::getBatchPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up using BigDecimal::add
    }

    public void cancelOrder() {
        this.status = OrderStatus.CANCELED;
    }

    public void returnOrder() {
        this.status = OrderStatus.RETURNED;
    }
}
