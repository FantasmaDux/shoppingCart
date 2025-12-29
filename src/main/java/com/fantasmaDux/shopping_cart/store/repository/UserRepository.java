package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
