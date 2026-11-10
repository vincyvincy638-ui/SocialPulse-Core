package com.media.social_media_backend.util;

import com.media.social_media_backend.dto.UserResponseDto;
import com.media.social_media_backend.entity.user;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto mapToUserResponseDto(user userEntity) {
        if (userEntity == null)
            return null;
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRole());
    }
}