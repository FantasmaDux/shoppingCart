package com.fantasmaDux.shopping_cart.api.dto;

import com.fantasmaDux.shopping_cart.store.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemDto {

    private UUID cartId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Product product;
}
