package com.fantasmaDux.shopping_cart.service.user;

import com.fantasmaDux.shopping_cart.api.dto.UserDto;
import com.fantasmaDux.shopping_cart.request.CreateUserRequest;
import com.fantasmaDux.shopping_cart.request.UpdateUserRequest;
import com.fantasmaDux.shopping_cart.store.model.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);

    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, UUID userId);

    void deleteUserById(UUID userId);

    UserDto convertUserToDto(User user);
}
