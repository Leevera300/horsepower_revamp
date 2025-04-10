package com.horsepower.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
public class ProductResponseDto {

    private Long id;
    private String name;
    private String category;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

