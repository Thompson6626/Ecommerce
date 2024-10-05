package com.ecommerce.ecommerce.order;

import com.ecommerce.ecommerce.common.PageResponse;
import com.ecommerce.ecommerce.order.dto.OrderRequest;
import com.ecommerce.ecommerce.order.dto.OrderResponse;
import com.ecommerce.ecommerce.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> makeOrder(
            @Valid @RequestBody OrderRequest request,
            @AuthenticationPrincipal User user
        ){
        var response = orderService.makeOrder(request,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<PageResponse<OrderResponse>> getHistory(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "sort", defaultValue = "orderDate", required = false) String sortBy,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(orderService.getHistory(page,size,sortBy,user));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable(name = "orderId") long orderId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(orderService.cancelOrder(orderId,user));
    }
    @PatchMapping("/{orderId}/return")
    public ResponseEntity<OrderResponse> returnOrder(
            @PathVariable(name = "orderId") long orderId,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(orderService.returnOrder(orderId,user));
    }

    @PatchMapping("/{orderId}/shipped")
    public ResponseEntity<OrderResponse> shipOrder(
            @PathVariable(name = "orderId") long orderId
    ){
        return ResponseEntity.ok(orderService.setOrderShipped(orderId));
    }
    @PatchMapping("/{orderId}/delivered")
    public ResponseEntity<OrderResponse> deliverOrder(
            @PathVariable(name = "orderId") long orderId
    ){
        return ResponseEntity.ok(orderService.setOrderDelivered(orderId));
    }
}
