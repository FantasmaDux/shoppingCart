package com.fantasmaDux.shopping_cart.service.category;

import com.fantasmaDux.shopping_cart.store.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category getCategoryById(UUID id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategoryById(Category category, UUID id);

    void deleteCategoryById(UUID id);
}
