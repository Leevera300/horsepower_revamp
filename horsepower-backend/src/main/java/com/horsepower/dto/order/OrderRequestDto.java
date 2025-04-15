package com.horsepower.dto.order;

import com.horsepower.dto.address.AddressRequestDto;
import com.horsepower.entity.order.Order.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private Long userId;

    @Email(message = "{order.email.invalid}")
    private String email;

    @NotBlank(message = "{order.orderNumber.notblank}")
    private String orderNumber;

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
    public boolean isUserOrEmailProvided() {
        return userId != null || (email != null && !email.isBlank());
    }

    // ✅ 추가: 배송지 / 청구지 리스트 (2개)
    @NotNull(message = "{order.addresses.notnull}")
    @Size(min = 1, message = "{order.addresses.size}")
    @Valid
    private List<AddressRequestDto> addresses;
}


