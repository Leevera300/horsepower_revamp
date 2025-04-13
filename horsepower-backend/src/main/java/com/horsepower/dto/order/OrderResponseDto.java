package com.horsepower.dto.order;

import com.horsepower.entity.order.Order.PaymentType;
import com.horsepower.entity.order.Order.Status;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for order response data.
 * Contains all necessary information about an order that should be returned to the client.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseDto {

    @NotNull
    private Long orderId;

    @NotBlank
    private String orderNumber;

    private Long userId;

    @Email
    private String email;

    @NotNull
    private Long productId;

    @NotNull
    private Long productDetailId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Integer totalPrice;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private Status orderStatus;

    @NotNull
    private LocalDateTime createdAt;
}

