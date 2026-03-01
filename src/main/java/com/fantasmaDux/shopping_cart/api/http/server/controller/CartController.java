package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.service.user.UserService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(name = "cart_handling_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Operation(
            summary = "возвращает корзину по id корзины"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Корзина возвращена"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable UUID id) {
        Cart cart = cartService.getCart(id);
        return ResponseEntity.ok().body(new ApiResponse("Found", cart));
    }

    @Operation(
            summary = "вовзращает корзину по id пользователя"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Корзина возвращена"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("")
    public ResponseEntity<ApiResponse> getCartByUserId() {
        User user = userService.getAuthentificatedUser();
        Cart cart = cartService.getCartByUserId(user.getId());
        return ResponseEntity.ok().body(new ApiResponse("Found", cart));
    }

    @Operation(
            summary = "очищает корзину по id корзины"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Корзина очищена"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCartById(@PathVariable UUID id) {
        cartService.clearCart(id);
        return ResponseEntity.ok().body(new ApiResponse("Cleared", cartService.getCart(id)));
    }

    @Operation(
            summary = "вычисляет итоговую стоимость содержимого корзины"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Итоговая стоимость возвращена"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{id}/totalPrice")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable UUID id) {
        BigDecimal totalPrice = cartService.getTotalPrice(id);
        return ResponseEntity.ok().body(new ApiResponse("Found", totalPrice));
    }
}
