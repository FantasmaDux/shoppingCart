package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.dto.UserDto;
import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.request.CreateUserRequest;
import com.fantasmaDux.shopping_cart.request.UpdateUserRequest;
import com.fantasmaDux.shopping_cart.service.user.UserService;
import com.fantasmaDux.shopping_cart.store.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "user_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api-prefix}/users")
public class UserController {
    private final UserService userService;


    @Operation(
            summary = "возвращает пользователя по id"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Пользователь возвращен"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.ok().body(new ApiResponse("Success", userDto));
    }


    @Operation(
            summary = "создает пользователя"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Пользователь создан"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        UserDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.ok().body(new ApiResponse("Create user success", userDto));
    }


    @Operation(
            summary = "обновляет пользователя по id"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Пользователь обновлен"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,
                                                  @PathVariable UUID userId) {
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.ok().body(new ApiResponse("Update user success", userDto));
    }


    @Operation(
            summary = "удаляет пользователя по id"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Пользователь удален"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().body(new ApiResponse("Delete user success", null));
    }
}
