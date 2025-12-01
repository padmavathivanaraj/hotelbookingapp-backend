package com.example.hotelbooking_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    // we use email + password for login in your AuthService
    private String email;
    private String password;
}
