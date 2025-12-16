package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
