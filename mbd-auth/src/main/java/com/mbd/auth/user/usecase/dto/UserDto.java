package com.mbd.auth.user.usecase.dto;

import com.mbd.auth.user.domain.UserId;
import lombok.Builder;

@Builder
public record UserDto(UserId id, String name, String email) {

    public static UserDto of(UserId id, String name, String email) {
        return UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
    }
}
