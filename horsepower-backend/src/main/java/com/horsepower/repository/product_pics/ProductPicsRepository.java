package com.horsepower.repository.product_pics;

import com.horsepower.entity.product_pics.ProductPics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPicsRepository extends JpaRepository<ProductPics, Long> {

    List<ProductPics> findByProductId(Long productId);
}

