package com.fantasmaDux.shopping_cart.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {
    private UUID imageId;
    private String imageName;
    private String downloadUrl;
}
