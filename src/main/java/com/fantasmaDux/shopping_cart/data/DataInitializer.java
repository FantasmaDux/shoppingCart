package com.fantasmaDux.shopping_cart.data;

import com.fantasmaDux.shopping_cart.store.model.Role;
import com.fantasmaDux.shopping_cart.store.model.User;
import com.fantasmaDux.shopping_cart.store.repository.CartRepository;
import com.fantasmaDux.shopping_cart.store.repository.RoleRepository;
import com.fantasmaDux.shopping_cart.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN");
        createDefaultRoleIfNotExists(roles);
//        createDefaultUserIfNotExists();
//        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role role = roleRepository.findByName("ROLE_USER").get();
        for (int i = 0; i < 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setEmail(defaultEmail);
            user.setFirstName("User" + i);
            user.setLastName("User" + i);
            user.setRoles(Set.of(role));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            log.info("User created: {}", user);

        }
    }

    private void createDefaultAdminIfNotExists() {
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 0; i < 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setEmail(defaultEmail);
            user.setFirstName("Admin" + i);
            user.setLastName("Admin" + i);
            user.setRoles(Set.of(role));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            log.info("Admin created: {}", user);

        }
    }


    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles
                .stream()
                .map(Role::new).forEach(roleRepository::save);
    }

}
