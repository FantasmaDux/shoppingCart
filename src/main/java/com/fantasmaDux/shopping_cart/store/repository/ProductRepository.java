package com.fantasmaDux.shopping_cart.store.repository;

import com.fantasmaDux.shopping_cart.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByBrandAndCategoryName(String brand, String category);

    Long countByBrandAndName(String brand, String name);

    @Query(
            "SELECT p FROM Product p WHERE " +
                    "(:includeMin = true AND p.price >= :minPrice OR " +
                    ":includeMin = false AND p.price > :minPrice) AND " +
                    "(:includeMax = true AND p.price <= :maxPrice OR " +
                    ":includeMax = false AND p.price < :maxPrice)"
    )
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, boolean includeMin, boolean includeMax);

    boolean existsByNameAndBrand(String name, String brand);
}
