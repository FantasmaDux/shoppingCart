package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.service.cartItem.CartItemService;
import com.fantasmaDux.shopping_cart.service.user.UserService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.User;
import com.fantasmaDux.shopping_cart.store.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/item")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam UUID productId,
            @RequestParam Integer quantity) {

        User user = userService.getAuthentificatedUser();
        Cart cart = cartService.initializeNewCart(user);

        cartItemService.addItemToCart(cart.getId(), productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Add item success", null));
    }

    @DeleteMapping("/{cartId}/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable UUID cartId,
                                                          @PathVariable UUID itemId) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Remove item success", null));
    }

    @PutMapping("/{cartId}/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable UUID cartId,
            @PathVariable UUID itemId,
            @RequestParam Integer quantity
    ) {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new ApiResponse("Update item quantity success", null));
    }
}
