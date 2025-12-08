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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") UUID id) {
        Product product = productService.getProductById(id);
        ProductDto convertedProduct = productService.convertToDto(product);
        return ResponseEntity.ok().body(new ApiResponse("success", convertedProduct));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        Product newProduct = productService.addProduct(product);
        ProductDto convertedNewProduct = productService.convertToDto(newProduct);
        return ResponseEntity.ok(new ApiResponse("Add product success", convertedNewProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") UUID id, @RequestBody UpdateProductRequest
            request) {
        Product updatedProduct = productService.updateProductById(id, request);
        ProductDto convertedUpdatedProduct = productService.convertToDto(updatedProduct);
        return ResponseEntity.ok().body(new ApiResponse("Updated product success", convertedUpdatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().body(new ApiResponse("delete product success", null));
    }

    @GetMapping("product/by/{brand}-and-{name}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @PathVariable String brand, @PathVariable String name
    ) {
        List<Product> products = productService.getProductsByBrandAndName(brand, name);

        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("product/by/{category}-and-{brand}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
            @PathVariable String category, @PathVariable String brand
    ) {
        List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("product/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

        return ResponseEntity.ok().body(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("product/count/by/{brand}-and-{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @PathVariable String brand, @PathVariable String name
    ) {
        var productCount = productService.countProductsByBrandAndName(brand, name);
        return ResponseEntity.ok().body(new ApiResponse("product count", productCount));
    }
}
