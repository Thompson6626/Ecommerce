package com.ecommerce.ecommerce.product;

import com.ecommerce.ecommerce.product.category.Category;
import com.ecommerce.ecommerce.review.Review;
import com.ecommerce.ecommerce.order.orderItem.OrderItem;

import com.ecommerce.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock; 
    private Double rating;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Transient
    public double getRate() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            return 0.0;
        }
        var rate = this.reviews.stream().parallel()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        return Math.round(rate * 10.0) / 10.0;
    }
}
