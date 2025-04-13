package com.horsepower.dto.order;

import com.horsepower.entity.order.Order.PaymentType;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    // 회원이면 userId, 비회원이면 email 필수
    private Long userId;

    @Email(message = "{order.email.invalid}")
    private String email;

    @NotNull(message = "{order.productId.notnull}")
    private Long productId;

    @NotNull(message = "{order.productDetailId.notnull}")
    private Long productDetailId;

    @NotNull(message = "{order.quantity.notnull}")
    @Positive(message = "{order.quantity.positive}")
    private Integer quantity;

    @NotNull(message = "{order.totalPrice.notnull}")
    @Positive(message = "{order.totalPrice.positive}")
    private Integer totalPrice;

    @NotNull(message = "{order.paymentType.notnull}")
    private PaymentType paymentType;

    @AssertTrue(message = "{order.userOrEmail.required}")
    private boolean isUserOrEmailProvided() {
        return userId != null || (email != null && !email.isBlank());
    }
}

