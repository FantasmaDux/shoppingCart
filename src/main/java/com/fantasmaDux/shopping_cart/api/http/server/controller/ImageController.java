package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.dto.ImageDto;
import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.service.image.ImageService;
import com.fantasmaDux.shopping_cart.store.model.Image;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Tag(name = "image_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/images")
public class ImageController {
    private final ImageService imageService;


    @Operation(
            summary = "сохраняет изображения товаров"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Изображения сохранены"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PostMapping("")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
                                                  @RequestParam UUID productId) {
        List<ImageDto> imageDtos = imageService.saveImages(files, productId);
        return ResponseEntity.ok(new ApiResponse("Upload success", imageDtos));
    }


    @Operation(
            summary = "предоставляет ссылку на скачивание изображения товара"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ссылка на скачивание возвращена"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{imageId}")
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> downloadImage(@PathVariable UUID imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,
                (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);

    }


    @Operation(
            summary = "обновляет изображение товара"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Изображение обновлено"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable UUID imageId, @RequestBody MultipartFile file) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse("Update success", null));
    }


    @Operation(
            summary = "удаляет изображение товара"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Изображение удалено"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable UUID imageId) {
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(new ApiResponse("Delete success", null));
    }

}
