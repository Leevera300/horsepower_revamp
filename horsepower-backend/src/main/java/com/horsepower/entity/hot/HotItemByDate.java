package com.horsepower.entity.hot;

import com.horsepower.entity.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "hot_items_by_date",
    uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "order_date"}),
    indexes = @Index(name = "idx_order_date", columnList = "order_date")
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotItemByDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 어떤 상품이 인기인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ 어떤 날짜에
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    // ✅ 누적 판매 수량
    @Column(name = "purchase_count", nullable = false)
    @Min(value = 0, message = "Purchase count cannot be negative")
    private Integer purchaseCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ 누적 증가용 메서드
    public void increaseCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        this.purchaseCount += count;
    }

    // ✅ 주문 취소 시 수량 감소
    public void decreaseCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        if (this.purchaseCount < count) {
            throw new IllegalStateException("Cannot decrease count below zero");
        }
        this.purchaseCount -= count;
    }

    // ✅ 초기화 생성자
    public static HotItemByDate create(Product product, LocalDate orderDate) {
        return HotItemByDate.builder()
                .product(product)
                .orderDate(orderDate)
                .purchaseCount(0)
                .build();
    }
}

