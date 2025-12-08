package com.fantasmaDux.shopping_cart.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {
    private UUID id;
    private String fileName;
    private String downloadUrl;
}
