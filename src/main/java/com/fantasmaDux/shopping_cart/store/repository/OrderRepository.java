package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
}
