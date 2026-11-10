package com.media.social_media_backend.Service;

import com.media.social_media_backend.dto.PostResponseDto;
import com.media.social_media_backend.entity.Post;
import com.media.social_media_backend.entity.user;
import com.media.social_media_backend.util.UserMapper;
import com.media.social_media_backend.repository.PostRepository;
import com.media.social_media_backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, UserMapper userMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // 1. Create Post
    public PostResponseDto createPost(Long userId, String content) {
        user userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(content);
        post.setUser(userEntity);

        Post savedPost = postRepository.save(post);
        return mapToPostResponseDto(savedPost);
    }

    // 2. Get All Posts (Latest posts first-ah varum!)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    // 3. Get Post By ID
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToPostResponseDto(post);
    }

    // 4. Get Posts By User ID
    public List<PostResponseDto> getPostsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(this::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    // 5. Get User Feed with Pagination (👈 Puthusa add panniyachu!)
    public Page<PostResponseDto> getUserFeed(Long userId, Pageable pageable) {
        user user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<user> followingUsers = user.getFollowing();
        followingUsers.add(user); // Include user's own posts in feed too!

        return postRepository.findByUserInOrderByCreatedAtDesc(followingUsers, pageable)
                .map(this::mapToPostResponseDto);
    }

    // Helper mapper method
    private PostResponseDto mapToPostResponseDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                userMapper.mapToUserResponseDto(post.getUser()), // Safe user dto!
                post.getCreatedAt());
    }
}