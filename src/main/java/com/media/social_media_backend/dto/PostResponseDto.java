package com.media.social_media_backend.dto;

import java.time.LocalDateTime;

public class PostResponseDto {
    private Long id;
    private String content;
    private UserResponseDto user; // 👈 Safe user DTO (no password!)
    private LocalDateTime createdAt;

    // Constructors
    public PostResponseDto() {
    }

    public PostResponseDto(Long id, String content, UserResponseDto user, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}