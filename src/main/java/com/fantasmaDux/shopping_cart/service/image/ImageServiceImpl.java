package com.fantasmaDux.shopping_cart.service.image;

import com.fantasmaDux.shopping_cart.api.dto.ImageDto;
import com.fantasmaDux.shopping_cart.api.exception.ImageNotFoundException;
import com.fantasmaDux.shopping_cart.service.product.ProductService;
import com.fantasmaDux.shopping_cart.store.model.Image;
import com.fantasmaDux.shopping_cart.store.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("Image is not found with id: " + id));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, UUID productId) {
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = createImage(file, productId);
            Image savedImage = imageRepository.save(image);

            String downloadUrl = "/api/v1/images/image/download/" + savedImage.getId();
            savedImage.setDownloadUrl(downloadUrl);
            imageRepository.save(savedImage);

            ImageDto imageDto = createImageDto(savedImage, downloadUrl);
            savedImageDto.add(imageDto);
        }
        return savedImageDto;
    }

    private Image createImage(MultipartFile file, UUID productId) {
        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setProduct(productService.getProductById(productId));
            return image;

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to create image: " + e.getMessage(), e);
        }
    }

    private ImageDto createImageDto(Image image, String downloadUrl) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setFileName(image.getFileName());
        imageDto.setDownloadUrl(downloadUrl);
        return imageDto;
    }

    @Override
    public void updateImage(MultipartFile file, UUID imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteImageById(UUID id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ImageNotFoundException("Image is not found with id: " + id);
        });
    }
}
