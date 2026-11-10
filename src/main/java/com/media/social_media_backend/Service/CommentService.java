package com.media.social_media_backend.Service;

import com.media.social_media_backend.dto.CommentResponseDto;
import com.media.social_media_backend.entity.Comment;
import com.media.social_media_backend.entity.Post;
import com.media.social_media_backend.entity.user;
import com.media.social_media_backend.util.UserMapper;
import com.media.social_media_backend.repository.CommentRepository;
import com.media.social_media_backend.repository.PostRepository;
import com.media.social_media_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper; // 👈 1. UserMapper inject panrom

    public CommentService(CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            UserMapper userMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // 1. Add Comment (Returns CommentResponseDto)
    public CommentResponseDto addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        user userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(userEntity);

        Comment savedComment = commentRepository.save(comment);

        // Entity to DTO conversion
        return mapToCommentResponseDto(savedComment);
    }

    // 2. Get Comments by Post ID (Returns List<CommentResponseDto>)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::mapToCommentResponseDto)
                .collect(Collectors.toList());
    }

    // Helper method to convert Comment entity to CommentResponseDto
    private CommentResponseDto mapToCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                userMapper.mapToUserResponseDto(comment.getUser()), // Safe user dto!
                comment.getCreatedAt());
    }
}