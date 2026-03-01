package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.dto.OrderDto;
import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.order.OrderService;
import com.fantasmaDux.shopping_cart.store.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "order_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/orders")
public class OrderController {
    private final OrderService orderService;


    @Operation(
            summary = "создает заказ"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Заказ создан"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PostMapping("")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody UUID userId) {
        Order order = orderService.placeOrder(userId);
        OrderDto orderDto = orderService.convertToOrderDto(order);
        return ResponseEntity.ok(new ApiResponse("Item order success", orderDto));
    }


    @Operation(
            summary = "возвращает заказ по id заказа"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Заказ возвращен"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable UUID orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Success", order));
    }


    @Operation(
            summary = "возвращает все заказы пользователя по id пользователя"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Заказы пользователя возвращены"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable UUID userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Success", orders));
    }

}
