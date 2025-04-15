package com.horsepower.repository.hot;

import com.horsepower.entity.hot.HotItemByDate;
import com.horsepower.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotItemByDateRepository extends JpaRepository<HotItemByDate, Long> {
    
    // 특정 상품의 특정 날짜 기록 조회
    Optional<HotItemByDate> findByProductAndOrderDate(Product product, LocalDate orderDate);
    
    // 특정 날짜의 인기 상품 조회 (판매량 기준 내림차순)
    @Query("SELECT h FROM HotItemByDate h WHERE h.orderDate = :date ORDER BY h.purchaseCount DESC")
    Page<HotItemByDate> findTopItemsByDate(@Param("date") LocalDate date, Pageable pageable);
    
    // 특정 기간의 인기 상품 조회
    @Query("SELECT h FROM HotItemByDate h WHERE h.orderDate BETWEEN :startDate AND :endDate ORDER BY h.purchaseCount DESC")
    Page<HotItemByDate> findTopItemsByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
    
    // 특정 상품의 특정 기간 판매량 합계
    @Query("SELECT COALESCE(SUM(h.purchaseCount), 0) FROM HotItemByDate h WHERE h.product = :product AND h.orderDate BETWEEN :startDate AND :endDate")
    Integer getTotalPurchaseCountByProductAndDateRange(
            @Param("product") Product product,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
} 