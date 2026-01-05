package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.service.user.UserService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable UUID id) {
        Cart cart = cartService.getCart(id);
        return ResponseEntity.ok().body(new ApiResponse("Found", cart));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getCartByUserId() {
        User user = userService.getAuthentificatedUser();
        Cart cart = cartService.getCartByUserId(user.getId());
        return ResponseEntity.ok().body(new ApiResponse("Found", cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCartById(@PathVariable UUID id) {
        cartService.clearCart(id);
        return ResponseEntity.ok().body(new ApiResponse("Cleared", cartService.getCart(id)));
    }

    @GetMapping("/{id}/totalPrice")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable UUID id) {
        BigDecimal totalPrice = cartService.getTotalPrice(id);
        return ResponseEntity.ok().body(new ApiResponse("Found", totalPrice));
    }
}
