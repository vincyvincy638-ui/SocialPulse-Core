package com.media.social_media_backend.Service;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.dto.LoginRequest;
import com.media.social_media_backend.dto.RegisterRequest;
import com.media.social_media_backend.dto.UserUpdateDto;
import com.media.social_media_backend.entity.user;
import com.media.social_media_backend.repository.UserRepository;
import com.media.social_media_backend.security.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ApiResponse registerUser(RegisterRequest request) {
        String email = request.getUserOfficialEmail() != null ? request.getUserOfficialEmail().trim().toLowerCase()
                : "";

        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            return new ApiResponse(false, "Email is already registered", null);
        }

        user newUser = new user();
        newUser.setUsername(request.getUserName());
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole() != null ? request.getRole() : "USER");

        try {
            user savedUser = userRepository.saveAndFlush(newUser);
            return new ApiResponse(true, "User registered successfully", savedUser);
        } catch (DataIntegrityViolationException exception) {
            return new ApiResponse(false, "Email is already registered", null);
        }
    }

    public ApiResponse loginUser(LoginRequest request) {
        String email = request.getUserOfficialEmail() != null ? request.getUserOfficialEmail().trim().toLowerCase()
                : "";

        Optional<user> userOpt = userRepository.findByEmailIgnoreCase(email);
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "User not found with this email", null);
        }

        user foundUser = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
            return new ApiResponse(false, "Invalid password", null);
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(foundUser.getEmail(), foundUser.getRole());

        // Package token and user details together
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("user", foundUser);

        return new ApiResponse(true, "Login successful", responseData);
    }

    // --- PROFILE UPDATE METHOD (👈 Puthusa add panniyachu!) ---
    public ApiResponse updateUserProfile(Long userId, UserUpdateDto request) {
        user existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            existingUser.setUsername(request.getUsername().trim());
        }

        if (request.getUserOfficialEmail() != null && !request.getUserOfficialEmail().trim().isEmpty()) {
            String newEmail = request.getUserOfficialEmail().trim().toLowerCase();

            // Check if email is already taken by another user
            Optional<user> emailCheck = userRepository.findByEmailIgnoreCase(newEmail);
            if (emailCheck.isPresent() && !emailCheck.get().getId().equals(userId)) {
                return new ApiResponse(false, "Email is already in use by another user", null);
            }
            existingUser.setEmail(newEmail);
        }

        user savedUser = userRepository.save(existingUser);
        return new ApiResponse(true, "Profile updated successfully", savedUser);
    }

    // --- FOLLOW / UNFOLLOW METHODS ---

    public void followUser(Long userId, Long targetUserId) {
        user user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        if (user.getId().equals(targetUser.getId())) {
            throw new RuntimeException("You cannot follow yourself!");
        }

        user.getFollowing().add(targetUser);
        userRepository.save(user);
    }

    public void unfollowUser(Long userId, Long targetUserId) {
        user user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        user.getFollowing().remove(targetUser);
        userRepository.save(user);
    }

    // --- ADMIN METHODS ---

    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}