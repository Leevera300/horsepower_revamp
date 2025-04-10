package com.horsepower.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horsepower.entity.product.Product;

@Repository // 이 인터페이스가 데이터베이스 접근용 Repository임을 나타냅니다.
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository<엔티티 클래스, 기본키 타입>
    // → 기본적인 CRUD 기능은 여기서 자동으로 제공됨
}

