package com.horsepower.entity.address;

import com.horsepower.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    public enum Type {
        DELIVERY, BILLING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 비회원도 입력 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @NotBlank
    @Column(name = "order_number")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private Type type;

    @NotBlank
    @Column(name = "country_region", length = 48)
    private String countryRegion;

    @NotBlank
    @Column(name = "first_name", length = 20)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", length = 20)
    private String lastName;

    @NotBlank
    @Column(name = "address1", length = 128)
    private String address1;

    @Column(name = "address2", length = 128)
    private String address2;

    @NotBlank
    @Column(name = "postal_code", length = 12)
    private String postalCode;

    @NotBlank
    @Column(length = 48)
    private String city;

    @Column(length = 48)
    private String state;

    @NotBlank
    @Column(name = "phone_number", length = 32)
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

