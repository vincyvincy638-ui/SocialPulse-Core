package com.media.social_media_backend.Service;

import com.media.social_media_backend.dto.ApiResponse;
import com.media.social_media_backend.entity.Like;
import com.media.social_media_backend.entity.Post;
import com.media.social_media_backend.entity.user;
import com.media.social_media_backend.repository.LikeRepository;
import com.media.social_media_backend.repository.PostRepository;
import com.media.social_media_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Toggle Like (Like iruntha unlike aagum, illana like aagum)
    @Transactional
    public ApiResponse toggleLike(Long postId, Long userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<user> userOpt = userRepository.findById(userId);

        if (postOpt.isEmpty()) {
            return new ApiResponse(false, "Post not found", null);
        }
        if (userOpt.isEmpty()) {
            return new ApiResponse(false, "User not found", null);
        }

        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            // Already liked -> Unlike panrom
            likeRepository.deleteByUserIdAndPostId(userId, postId);
            return new ApiResponse(true, "Post unliked successfully", null);
        } else {
            // Not liked yet -> Like panrom
            Like like = new Like();
            like.setUser(userOpt.get());
            like.setPost(postOpt.get());
            likeRepository.save(like);
            return new ApiResponse(true, "Post liked successfully", null);
        }
    }

    // Total likes count edukka
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}