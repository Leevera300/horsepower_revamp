package com.horsepower.dto.product_pics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductPicsResponseDto {

    private Long id;
    private Long productId;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
