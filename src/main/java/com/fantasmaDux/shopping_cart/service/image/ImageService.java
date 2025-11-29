package com.fantasmaDux.shopping_cart.service.image;

import com.fantasmaDux.shopping_cart.api.dto.ImageDto;
import com.fantasmaDux.shopping_cart.store.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    Image getImageById(UUID id);
    List<ImageDto> saveImages(List<MultipartFile> files, UUID productId); // MultipartFile — это интерфейс в Spring
                                                         // для работы с загружаемыми файлами через HTTP-запросы.
    void updateImage(MultipartFile file, UUID imageId);
    void deleteImageById(UUID id);

}
