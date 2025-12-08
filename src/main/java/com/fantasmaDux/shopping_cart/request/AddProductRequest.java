package com.fantasmaDux.shopping_cart.request;

import com.fantasmaDux.shopping_cart.store.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

// используется, чтобы заменить прямую работу с entity
@Data
public class AddProductRequest {
    private UUID id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String category;

}
