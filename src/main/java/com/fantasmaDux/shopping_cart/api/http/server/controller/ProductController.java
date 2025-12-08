package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.dto.ProductDto;
import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.request.AddProductRequest;
import com.fantasmaDux.shopping_cart.request.UpdateProductRequest;
import com.fantasmaDux.shopping_cart.service.product.ProductService;
import com.fantasmaDux.shopping_cart.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.Exception;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") UUID id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto convertedProduct = productService.convertToDto(product);
            return ResponseEntity.ok().body(new ApiResponse("success", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(
                    new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product newProduct = productService.addProduct(product);
            ProductDto convertedNewProduct = productService.convertToDto(newProduct);
            return ResponseEntity.ok(new ApiResponse("Add product success", convertedNewProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") UUID id, @RequestBody UpdateProductRequest
            request) {
        try {
            Product updatedProduct = productService.updateProductById(id, request);
            ProductDto convertedUpdatedProduct = productService.convertToDto(updatedProduct);
            return ResponseEntity.ok().body(new ApiResponse("Updated product success", convertedUpdatedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") UUID id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok().body(new ApiResponse("delete product success", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/by/{brand}-and-{name}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @PathVariable String brand, @PathVariable String name
    ) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No products found"));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/by/{category}-and-{brand}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
            @PathVariable String category, @PathVariable String brand
    ) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No products found"));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No products found"));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No products found"));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No products found"));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/count/by/{brand}-and-{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @PathVariable String brand, @PathVariable String name
    ) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok().body(new ApiResponse("product count", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
