package com.horsepower.repository.order;

import com.horsepower.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 회원 주문 조회
    Page<Order> findByUser_Id(Long userId, Pageable pageable);
    
    // 비회원 주문 조회
    Page<Order> findByEmail(String email, Pageable pageable);

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);
} 