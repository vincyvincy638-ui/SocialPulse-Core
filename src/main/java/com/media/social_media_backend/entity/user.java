package com.media.social_media_backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // 👈 Self-referencing Many-to-Many for Follow / Unfollow System
    @ManyToMany
    @JoinTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<user> following = new HashSet<>();

    @ManyToMany(mappedBy = "following")
    private Set<user> followers = new HashSet<>();

    public user() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // 👈 Getters and Setters for Following & Followers
    public Set<user> getFollowing() {
        return following;
    }

    public void setFollowing(Set<user> following) {
        this.following = following;
    }

    public Set<user> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<user> followers) {
        this.followers = followers;
    }
}