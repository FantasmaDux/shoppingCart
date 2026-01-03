package com.fantasmaDux.shopping_cart.service.cart;

import com.fantasmaDux.shopping_cart.store.model.Cart;
import com.fantasmaDux.shopping_cart.store.model.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface CartService {

    Cart getCart(UUID id);

    void clearCart(UUID id);

    BigDecimal getTotalPrice(UUID id);

    Cart getCartByUserId(UUID userId);

    Cart initializeNewCart(User user);
}
