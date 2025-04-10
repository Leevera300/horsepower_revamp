package com.horsepower.entity.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity // 이 클래스는 DB 테이블과 매핑되는 엔티티 클래스입니다.
@Table(name = "product") // 실제 DB에서 테이블 이름을 "product"로 지정합니다.
@EntityListeners(AuditingEntityListener.class) // 👈 이거 꼭 있어야 자동 설정 됨!
@Getter // Lombok - 모든 필드에 대해 getter 메서드 자동 생성
@Setter // Lombok - 모든 필드에 대해 setter 메서드 자동 생성
@NoArgsConstructor // Lombok - 파라미터 없는 기본 생성자 자동 생성
public class Product {

    @Id // 기본 키(PK) 필드임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    private Long id;

    private String name; // 상품 이름
    
    private String category; // 상품 카테고리

    @Column(columnDefinition = "TEXT")
    private String description; // 상품 설명

    @CreatedDate // 등록 시간 자동 저장
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 시간 자동 저장
    private LocalDateTime updatedAt;
}

