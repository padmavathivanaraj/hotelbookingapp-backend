package com.example.hotelbooking_backend.controller;

import com.example.hotelbooking_backend.dto.LoginRequest;
import com.example.hotelbooking_backend.dto.RegisterRequest;
import com.example.hotelbooking_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; // <-- add this import

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            String token = authService.register(request);
            return ResponseEntity.status(201).body(new AuthResponse(token));
        } catch (Exception e) {
            // return controlled error response so Spring won't forward to /error (which can produce 403)
            return ResponseEntity.badRequest().body(Map.of(
                "error", "registration_failed",
                "message", e.getMessage() != null ? e.getMessage() : "Unknown error"
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "invalid_credentials",
                "message", e.getMessage() != null ? e.getMessage() : "Invalid email/password"
            ));
        }
    }

    // Simple response DTO
    public static record AuthResponse(String token) { }
}
