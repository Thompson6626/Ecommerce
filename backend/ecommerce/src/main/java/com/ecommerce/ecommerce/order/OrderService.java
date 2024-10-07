package com.ecommerce.ecommerce.order;

import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.exceptions.Identifier;
import com.ecommerce.ecommerce.exceptions.OrderNotFoundException;
import com.ecommerce.ecommerce.exceptions.UnauthorizedModificationException;
import com.ecommerce.ecommerce.order.dto.OrderRequest;
import com.ecommerce.ecommerce.order.dto.OrderResponse;
import com.ecommerce.ecommerce.order.orderItem.OrderItem;
import com.ecommerce.ecommerce.stripe.PaymentResponse;
import com.ecommerce.ecommerce.stripe.StripeService;
import com.ecommerce.ecommerce.user.User;
import com.ecommerce.ecommerce.utils.Utils;
import com.stripe.exception.StripeException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final StripeService stripeService;

    public PaymentResponse makeOrder(OrderRequest request, User user) throws StripeException {
        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        var fullPrice = BigDecimal.ZERO;

        for(OrderItem ot: order.getOrderItems()){
            ot.setOrder(order);
            fullPrice = fullPrice.add(ot.getBatchPrice());
        }

        order.setTotalPrice(fullPrice);

        order = orderRepository.save(order);
        
        var paymentResponse = stripeService.createPaymentIntent(order);

        return paymentResponse;
    }
    
    public OrderResponse getOrderById(
        long orderId,
        User user
        ){
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(Identifier.ID,orderId));

        if (!order.getUser().getId().equals(user.getId()) || 
        user.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_ADMIN"))
        ){
            throw new UnauthorizedModificationException("You cannot see other user's orders");
        }    
        return orderMapper.toResponse(order);
    }

    public PageResponse<OrderResponse> getHistory(
            int page,
            int size,
            String sortBy,
            User user
    ) {
        Pageable pageable = Utils.pageableSortedBy(page, size,sortBy,false);
        Page<Order> orders = orderRepository.findOrderByUserId(user.getId(),pageable);

        return Utils.generatePageResponse(orders,orderMapper::toResponse);
    }

    public OrderResponse cancelOrder(
            long orderId,
            User user
    ){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(Identifier.ID,orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new UnauthorizedModificationException("Cannot modify other user's orders");
        }

        order.cancelOrder();

        return orderMapper.toResponse(orderRepository.save(order));
    }

    public OrderResponse returnOrder(
            long orderId,
            User user
    ){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(Identifier.ID,orderId));

        if (!order.getUser().getId().equals(user.getId())){
            throw new UnauthorizedModificationException("Cannot modify other user's orders");
        }

        order.returnOrder();

        return orderMapper.toResponse(orderRepository.save(order));
    }
    // Admin/ logistics
    public OrderResponse setOrderShipped(long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(Identifier.ID,orderId));

        order.setStatus(OrderStatus.SHIPPED);
        return orderMapper.toResponse(orderRepository.save(order));
    }
    public OrderResponse setOrderDelivered(long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(Identifier.ID,orderId));

        order.setStatus(OrderStatus.DELIVERED);
        return orderMapper.toResponse(orderRepository.save(order));
    }

}
