package com.horsepower.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    @NotBlank(message = "한글 상품명은 필수입니다.")
    private String nameKr;

    @NotBlank(message = "영문 상품명은 필수입니다.")
    private String nameEng;

    private String descriptionKr;

    private String descriptionEng;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    private String subCategory;
}

