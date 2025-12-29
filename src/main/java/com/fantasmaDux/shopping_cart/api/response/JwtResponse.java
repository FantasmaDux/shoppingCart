package com.fantasmaDux.shopping_cart.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private UUID id;
    private String token;
}
