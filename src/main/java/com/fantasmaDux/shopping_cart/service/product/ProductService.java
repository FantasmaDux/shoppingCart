package com.fantasmaDux.shopping_cart.service.product;

import com.fantasmaDux.shopping_cart.store.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    // JPA will write these queries
    Product getProductById(UUID id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndCategory(String brand, String category);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getProductsByPriceRange(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            boolean includeMin,
            boolean includeMax
    );

    Long countProductsByBrandAndName(String brand, String name);

    Product addProduct(Product product);

    void deleteProductById(UUID id);

    void updateProductById(UUID id, Product product);

}
