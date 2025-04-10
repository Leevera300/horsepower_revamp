package com.horsepower.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter // 자동으로 getter 생성
@Setter // 자동으로 setter 생성
public class ProductRequestDto {

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    private Integer price;

    private String description;

    private String category;
}

