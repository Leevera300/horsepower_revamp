package com.horsepower.dto.product_detail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailRequestDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotBlank(message = "색상은 비어 있을 수 없습니다.")
    private String color;

    @NotBlank(message = "사이즈는 비어 있을 수 없습니다.")
    private String size;

    @NotNull(message = "수량은 필수입니다.")
    private Integer quantity;

    @NotNull(message = "가격은 필수입니다.")
    private Integer price;

    private Integer sale; // 선택 값 (nullable)
}

