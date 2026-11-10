package com.media.social_media_backend.dto;

public class UserUpdateDto {
    private String username;
    private String userOfficialEmail;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserOfficialEmail() {
        return userOfficialEmail;
    }

    public void setUserOfficialEmail(String userOfficialEmail) {
        this.userOfficialEmail = userOfficialEmail;
    }
}