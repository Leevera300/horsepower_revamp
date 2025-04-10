package com.horsepower.repository.product_detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.horsepower.entity.product_detail.ProductDetail;

import java.util.List;

@Repository // Spring이 자동으로 Bean으로 등록해줌
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    // 선택: 특정 상품 ID에 해당하는 상세옵션들 조회
    List<ProductDetail> findByProductId(Long productId);
}

