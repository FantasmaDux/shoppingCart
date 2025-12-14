package com.fantasmaDux.shopping_cart.service.cartItem;

import com.fantasmaDux.shopping_cart.store.model.CartItem;

import java.util.UUID;

public interface CartItemService {

    void addItemToCart(UUID cartId, UUID productId, int quantity);
    void removeItemFromCart(UUID cartId, UUID productId);
    void updateItemQuantity(UUID cartId, UUID productId, int quantity);
    CartItem getCartItem(UUID cartId, UUID productId);
}
