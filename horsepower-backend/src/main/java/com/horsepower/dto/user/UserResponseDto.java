package com.horsepower.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String message;
    private Object data;

    public static UserResponseDto of(String message) {
        return new UserResponseDto(message, null);
    }

    public static UserResponseDto of(String message, Object data) {
        return new UserResponseDto(message, data);
    }
} 