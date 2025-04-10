package com.horsepower.dto.product_detail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductDetailResponseDto {

    private Long id;
    private Long productId;
    private String color;
    private String size;
    private Integer quantity;
    private Integer price;
    private Integer sale;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

