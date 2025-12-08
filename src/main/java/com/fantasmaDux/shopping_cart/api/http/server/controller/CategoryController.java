package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.category.CategoryService;
import com.fantasmaDux.shopping_cart.store.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(new ApiResponse("Found", categories));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok().body(new ApiResponse("Success", category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(new ApiResponse("Found", category));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok().body(new ApiResponse("Found", category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().body(new ApiResponse("Deleted", null));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategoryById(category, id);
        return ResponseEntity.ok().body(new ApiResponse("Updated", updatedCategory));
    }

}
