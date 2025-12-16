package com.fantasmaDux.shopping_cart.api.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDto {
    private UUID id;
    private String productName;
    private BigDecimal price;
    private int quantity;

}
