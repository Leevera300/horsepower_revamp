package com.horsepower.dto.product_pics;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPicsRequestDto {

    @NotNull(message = "productId는 필수입니다.")
    private Long productId;

    @NotNull(message = "imagePath는 필수입니다.")
    private String imagePath;
}

