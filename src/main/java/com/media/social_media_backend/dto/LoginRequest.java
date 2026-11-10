package com.media.social_media_backend.dto;

public class LoginRequest {
    private String userOfficialEmail;
    private String password;

    // Getters and Setters
    public String getUserOfficialEmail() {
        return userOfficialEmail;
    }

    public void setUserOfficialEmail(String userOfficialEmail) {
        this.userOfficialEmail = userOfficialEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}