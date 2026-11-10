package com.media.social_media_backend.repository;

import com.media.social_media_backend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Oru user antha post-ku already like potrukaangala nu check panna
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    // Oru post-ku total evlo likes irukku nu count panna
    long countByPostId(Long postId);

    // Like-ai remove panna (Unlike)
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
