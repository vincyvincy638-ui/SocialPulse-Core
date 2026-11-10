package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.Service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    // 👈 Constructor name LikeController nu irukanum
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 1. Toggle Like: POST /api/likes/toggle?postId=1&userId=1
    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse> toggleLike(
            @RequestParam Long postId,
            @RequestParam Long userId) {
        ApiResponse response = likeService.toggleLike(postId, userId);
        return ResponseEntity.ok(response);
    }

    // 2. Get Like Count: GET /api/likes/count/1
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        long count = likeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }
}