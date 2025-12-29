package com.fantasmaDux.shopping_cart.service.user;

import com.fantasmaDux.shopping_cart.api.dto.UserDto;
import com.fantasmaDux.shopping_cart.api.exception.UserAlreadyExistsException;
import com.fantasmaDux.shopping_cart.api.exception.UserNotFoundException;
import com.fantasmaDux.shopping_cart.request.CreateUserRequest;
import com.fantasmaDux.shopping_cart.request.UpdateUserRequest;
import com.fantasmaDux.shopping_cart.store.model.User;
import com.fantasmaDux.shopping_cart.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration attempt with existing email: {}", request.getEmail());
            throw new UserAlreadyExistsException("Registration conflict");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);

    }

    @Override
    public User updateUser(UpdateUserRequest request, UUID userId) {

        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new UserNotFoundException();
        });

    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthentificatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }


}
