package com.horsepower.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CheckoutResponseDto {

    private Long id;
    private String userEmail;
    private Long productId;
    private Long productDetailId;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

