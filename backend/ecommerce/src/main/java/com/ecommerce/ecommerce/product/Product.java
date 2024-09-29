package com.ecommerce.ecommerce.product;

import com.ecommerce.ecommerce.category.Category;
import com.ecommerce.ecommerce.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double price;
    private String imageUrl;
    private Integer stock; 
    private Double rating;

    @ManyToOne
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews; 

    @Transient
    public double getRate() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            return 0.0;
        }
        var rate = this.reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;

        return roundedRate;
    }
}
