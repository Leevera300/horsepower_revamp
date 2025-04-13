package com.horsepower.entity.order;

import com.horsepower.entity.product.Product;
import com.horsepower.entity.product_detail.ProductDetail;
import com.horsepower.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Order {

    public enum Status {
        PENDING, PAID, SHIPPED, CANCELED
    }

    public enum PaymentType {
        CARD, KAKAO, NAVER, PAYPAL, TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{order.orderNumber.notblank}")
    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Email(message = "{order.email.invalid}")
    @NotBlank(message = "{order.email.notblank}")
    @Column(length = 128)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @NotNull(message = "{order.quantity.notnull}")
    @Positive(message = "{order.quantity.positive}")
    private Integer quantity;

    @NotNull(message = "{order.totalPrice.notnull}")
    @Positive(message = "{order.totalPrice.positive}")
    @Column(name = "total_price")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 16)
    private Status orderStatus = Status.PENDING;

    @NotNull(message = "{order.paymentType.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 16)
    private PaymentType paymentType;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

