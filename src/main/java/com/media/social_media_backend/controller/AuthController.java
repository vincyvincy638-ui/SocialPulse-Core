package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.dto.LoginRequest;
import com.media.social_media_backend.dto.RegisterRequest;
import com.media.social_media_backend.Service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        ApiResponse response = userService.registerUser(request);

        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Inga Login endpoint-ai include panniyachu 👇
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse response = userService.loginUser(request);

        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN) // 403 Status code expect panra maathiri
                    .body(response);
        }

        return ResponseEntity.ok(response); // Success-ana mattum 200 OK
    }
}