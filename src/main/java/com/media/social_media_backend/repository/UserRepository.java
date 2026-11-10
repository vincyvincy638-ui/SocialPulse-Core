
package com.media.social_media_backend.repository;

import com.media.social_media_backend.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {

    // Entity-la 'email' field irukkurathaala intha method name correct-ah work
    // aagum
    Optional<user> findByEmailIgnoreCase(String email);

}