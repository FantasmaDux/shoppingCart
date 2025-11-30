package com.fantasmaDux.shopping_cart.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// class for data returning to front
@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
}
