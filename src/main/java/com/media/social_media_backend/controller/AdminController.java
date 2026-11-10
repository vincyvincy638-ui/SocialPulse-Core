package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.entity.user;
import com.media.social_media_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 1. Ella users-oda details-aiyum paarkurathu (Admin Only) -> URL:
    // /api/admin/users
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<user> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse(true, "All users fetched successfully", users));
    }

    // 2. Oru user-ai ID vechu delete panrathu (Admin Only) -> URL:
    // /api/admin/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully", null));
        }
        return ResponseEntity.status(404).body(new ApiResponse(false, "User not found", null));
    }
}