package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.dto.OrderDto;
import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.order.OrderService;
import com.fantasmaDux.shopping_cart.store.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody UUID userId) {
        Order order = orderService.placeOrder(userId);
        OrderDto orderDto = orderService.convertToOrderDto(order);
        return ResponseEntity.ok(new ApiResponse("Item order success", orderDto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable UUID orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Success", order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable UUID userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

}
