package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.cart.CartService;
import com.fantasmaDux.shopping_cart.service.cartItem.CartItemService;
import com.fantasmaDux.shopping_cart.service.user.UserService;
import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "cart_items_handling_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    @Operation(
            summary = "добавляет товар в корзину"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Товар добавлен"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PostMapping("/item")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam UUID productId,
            @RequestParam Integer quantity) {

        User user = userService.getAuthentificatedUser();
        Cart cart = cartService.initializeNewCart(user);

        cartItemService.addItemToCart(cart.getId(), productId, quantity);
        return ResponseEntity.ok(new ApiResponse("Add item success", null));
    }


    @Operation(
            summary = "удаляет товар из корзины"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Товар удален"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @DeleteMapping("/{cartId}/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable UUID cartId,
                                                          @PathVariable UUID itemId) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Remove item success", null));
    }


    @Operation(
            summary = "обновляет количество товаров в корзине"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Количество товаров обновлено"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
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
