package com.example.hotelbooking_backend.controller;

import com.example.hotelbooking_backend.entity.Role;
import com.example.hotelbooking_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private RoleRepository roleRepo;

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody @NonNull Role role) {
        Role saved = roleRepo.save(role); // use the injected instance, not a static call
        return ResponseEntity.ok(saved);
    }
}


