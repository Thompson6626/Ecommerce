package com.ecommerce.ecommerce.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.user.id = :userId
            """)
    Page<Order> findOrderByUserId(@Param("userId") Integer userId, Pageable pageable);


}
