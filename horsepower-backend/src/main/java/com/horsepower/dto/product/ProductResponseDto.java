package com.horsepower.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String nameKr;
    private String nameEng;
    private String descriptionKr;
    private String descriptionEng;
    private String category;
    private String subCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


