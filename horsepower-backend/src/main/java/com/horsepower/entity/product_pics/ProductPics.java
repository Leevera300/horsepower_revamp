package com.horsepower.entity.product_pics;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_pics")
@Getter
@Setter
@NoArgsConstructor
public class ProductPics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // 상품 ID (FK 없이 매핑)

    private String imagePath; // 이미지 경로 또는 URL

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

