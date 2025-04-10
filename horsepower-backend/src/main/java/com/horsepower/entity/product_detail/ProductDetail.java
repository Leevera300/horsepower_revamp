package com.horsepower.entity.product_detail;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_detail")
@Getter
@Setter
@NoArgsConstructor
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // 실제로는 Product와 관계 있지만 FK 없이 매핑

    private String color;
    private String size;
    private Integer quantity;
    private Integer sale;  // 세일률 or 세일 여부
    private Integer price;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
