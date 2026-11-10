package com.media.social_media_backend.repository;

import com.media.social_media_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Oru specific post-ku irukkura ella comments-aiyum edukka
    List<Comment> findByPostId(Long postId);
}