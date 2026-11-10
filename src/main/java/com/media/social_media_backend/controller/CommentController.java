package com.media.social_media_backend.controller;

import com.media.social_media_backend.dto.CommentResponseDto;
import com.media.social_media_backend.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Add Comment
    @PostMapping("/add")
    public ResponseEntity<CommentResponseDto> addComment(
            @RequestParam Long postId,
            @RequestParam Long userId,
            @RequestBody String content) {
        CommentResponseDto response = commentService.addComment(postId, userId, content);
        return ResponseEntity.ok(response);
    }

    // Get Comments for Post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}