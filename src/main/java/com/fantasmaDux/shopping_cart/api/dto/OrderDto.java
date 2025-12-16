package com.fantasmaDux.shopping_cart.api.dto;

import com.fantasmaDux.shopping_cart.store.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private UUID userId;
    private LocalDate orderDate;
    private BigDecimal orderTotalAmount;
    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItems;

}
