package com.horsepower.dto.checkout;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequestDto {

    @NotNull(message = "이메일은 필수입니다.")
    private String userEmail;

    @NotNull(message = "productId는 필수입니다.")
    private Long productId;

    @NotNull(message = "productDetailId는 필수입니다.")
    private Long productDetailId;

    @NotNull(message = "수량은 필수입니다.")
    private Integer quantity;
}

