package com.media.social_media_backend.repository;

import com.media.social_media_backend.entity.Post;
import com.media.social_media_backend.entity.user;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 1. Get posts by user ID
    List<Post> findByUserId(Long userId);

    // 2. Get all posts with latest posts first
    List<Post> findAllByOrderByCreatedAtDesc();

    // 3. Paginated all posts (👈 Pagination-ku mukkiyam!)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 4. Paginated custom feed for follow system (👈 Feed-ku mukkiyam!)
    Page<Post> findByUserInOrderByCreatedAtDesc(Set<user> users, Pageable pageable);
}