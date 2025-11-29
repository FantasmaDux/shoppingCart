package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
