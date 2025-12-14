package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable UUID id) {
        Cart cart = cartService.getCart(id);
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
