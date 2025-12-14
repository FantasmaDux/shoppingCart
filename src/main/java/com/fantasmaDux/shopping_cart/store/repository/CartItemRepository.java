package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    void deleteAllByCartId(UUID id);
}
