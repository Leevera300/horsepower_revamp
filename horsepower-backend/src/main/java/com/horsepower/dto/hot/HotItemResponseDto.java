package com.horsepower.dto.hot;

import com.horsepower.entity.hot.HotItemByDate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HotItemResponseDto {
    private Long id;
    private Long productId;
    private String nameKr;
    private String nameEng;
    private LocalDate orderDate;
    private Integer purchaseCount;

    public static HotItemResponseDto from(HotItemByDate hotItem) {
        return HotItemResponseDto.builder()
                .id(hotItem.getId())
                .productId(hotItem.getProduct().getId())
                .nameKr(hotItem.getProduct().getNameKr())
                .nameEng(hotItem.getProduct().getNameEng())
                .orderDate(hotItem.getOrderDate())
                .purchaseCount(hotItem.getPurchaseCount())
                .build();
    }
} 