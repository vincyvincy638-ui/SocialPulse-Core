package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.dto.UserUpdateDto;
import com.media.social_media_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Update User Profile Endpoint
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserUpdateDto request) {
        ApiResponse response = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}