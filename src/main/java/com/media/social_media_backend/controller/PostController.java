package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.PostResponseDto;
import com.media.social_media_backend.Service.PostService;
import com.media.social_media_backend.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService; // 👈 UserService inject panrom for follow/unfollow

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    // 1. Create Post
    @PostMapping("/create")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestParam Long userId,
            @RequestBody String content) {
        PostResponseDto createdPost = postService.createPost(userId, content);
        return ResponseEntity.ok(createdPost);
    }

    // 2. Get All Posts (Latest first-ah varum!)
    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 3. Get Post By ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // 4. Get Posts By User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUserId(@PathVariable Long userId) {
        List<PostResponseDto> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // 5. Follow User Endpoint (👈 Puthusa add panniyachu!)
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(
            @RequestParam Long userId,
            @RequestParam Long targetUserId) {
        userService.followUser(userId, targetUserId);
        return ResponseEntity.ok("Successfully followed user!");
    }

    // 6. Unfollow User Endpoint (👈 Puthusa add panniyachu!)
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(
            @RequestParam Long userId,
            @RequestParam Long targetUserId) {
        userService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok("Successfully unfollowed user!");
    }

    // 7. Get User Feed with Pagination (👈 Puthusa add panniyachu!)
    @GetMapping("/feed/{userId}")
    public ResponseEntity<Page<PostResponseDto>> getUserFeed(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponseDto> feed = postService.getUserFeed(userId, pageable);
        return ResponseEntity.ok(feed);
    }
}