package com.fantasmaDux.shopping_cart.service.product;

import com.fantasmaDux.shopping_cart.api.exception.ProductNotFoundException;
import com.fantasmaDux.shopping_cart.store.model.Product;
import com.fantasmaDux.shopping_cart.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found")
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String category) {
        return productRepository.findByBrandAndCategoryName(brand, category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice,
                                                 BigDecimal maxPrice,
                                                 boolean includeMin,
                                                 boolean includeMax) {
        return productRepository.findByPriceRange(minPrice, maxPrice, includeMin, includeMax);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
        throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public void updateProductById(UUID id, Product product) {

    }
}
