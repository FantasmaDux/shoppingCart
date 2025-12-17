package com.fantasmaDux.shopping_cart.api.dto;

import com.fantasmaDux.shopping_cart.store.model.CartItem;
import com.fantasmaDux.shopping_cart.store.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class CartDto {

    private UUID cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
