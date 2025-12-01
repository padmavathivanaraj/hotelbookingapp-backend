package com.example.hotelbooking_backend.repository;
import com.example.hotelbooking_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);                // used in AuthService.register/login
    Optional<User> findByUsername(String username);          // helpful
    Optional<User> findByUsernameOrEmail(String username, String email); // optional: used by UserDetailsService
}
