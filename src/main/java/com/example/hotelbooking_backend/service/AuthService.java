package com.example.hotelbooking_backend.service;

import com.example.hotelbooking_backend.config.JwtUtil;
import com.example.hotelbooking_backend.dto.LoginRequest;
import com.example.hotelbooking_backend.dto.RegisterRequest;
import com.example.hotelbooking_backend.entity.User;
import com.example.hotelbooking_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest req) {
        // check existing by email or username
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);

        return jwtUtil.generateJwtToken(user.getUsername());
    }

    public String login(LoginRequest req) {
        // try by email first, then username
        Optional<User> maybe = userRepository.findByEmail(req.getEmail());
        if (maybe.isEmpty() && req instanceof LoginRequest) {
            // if LoginRequest uses username field instead, try username:
            // optional: userRepository.findByUsername(req.getEmail());
            maybe = userRepository.findByUsername(req.getEmail());
        }

        User user = maybe.orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateJwtToken(user.getUsername());
    }
}
