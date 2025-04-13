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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_kr")
    private String nameKr;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(columnDefinition = "TEXT", name = "description_kr")
    private String descriptionKr;

    @Column(columnDefinition = "TEXT", name = "description_eng")
    private String descriptionEng;

    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
