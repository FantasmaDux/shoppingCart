package com.fantasmaDux.shopping_cart.api.http.server.controller;

import com.fantasmaDux.shopping_cart.api.response.ApiResponse;
import com.fantasmaDux.shopping_cart.api.response.JwtResponse;
import com.fantasmaDux.shopping_cart.request.LoginRequest;
import com.fantasmaDux.shopping_cart.security.jwt.JwtUtils;
import com.fantasmaDux.shopping_cart.security.user.ShopUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authorization_methods")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api-prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "выдает токены пользователю",
            description = "Получает запрос на вход, возвращает токены " +
                    "для взаимодействия с защищенными методами"
    )

    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешный вход — возвращает JWT токен"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Неверный запрос (например, некорректный email или пароль)"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
        return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));
    }
}
