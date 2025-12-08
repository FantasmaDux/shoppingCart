package com.fantasmaDux.shopping_cart.api.dto;

import com.fantasmaDux.shopping_cart.store.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String name;
    private String brand;
    private BigDecimal price; // Точность операций
    private int inventory; // Количество на складе, чтобы не путать с заказанным количеством
    private String description;

    // Категория и продукт не зависят друг от друга
    private Category category;

    private List<ImageDto> images;
}
