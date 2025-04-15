package com.horsepower.service.hot;

import com.horsepower.dto.hot.HotItemResponseDto;
import com.horsepower.entity.hot.HotItemByDate;
import com.horsepower.entity.product.Product;
import com.horsepower.exception.BusinessException;
import com.horsepower.repository.hot.HotItemByDateRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotItemService {

    private final HotItemByDateRepository hotItemRepository;
    private final MessageSource messageSource;
    private static final int MAX_DATE_RANGE_DAYS = 365; // 1년으로 제한

    /**
     * 주문 생성 시 판매량 증가
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increasePurchaseCount(Product product, int quantity) {
        try {
            if (quantity <= 0) {
                throw new BusinessException(
                        messageSource.getMessage("hot.item.quantity.invalid", null, Locale.getDefault()));
            }

            LocalDate today = LocalDate.now();
            
            // 기존 기록 조회 또는 새로 생성
            HotItemByDate hotItem = hotItemRepository.findByProductAndOrderDate(product, today)
                    .orElseGet(() -> {
                        HotItemByDate newItem = HotItemByDate.create(product, today);
                        log.info("Creating new hot item record for product: {} on date: {}", product.getId(), today);
                        return hotItemRepository.save(newItem);
                    });
            
            // 판매량 증가
            hotItem.increaseCount(quantity);
            hotItemRepository.save(hotItem);
            log.info("Increased purchase count for product: {} by {}", product.getId(), quantity);
        } catch (Exception e) {
            log.error("Failed to update hot items for product: {}", product.getId(), e);
            throw e;
        }
    }

    /**
     * 주문 취소 시 판매량 감소
     */
    @Transactional
    public void decreasePurchaseCount(Product product, int quantity) {
        if (quantity <= 0) {
            throw new BusinessException(
                    messageSource.getMessage("hot.item.quantity.invalid", null, Locale.getDefault()));
        }

        LocalDate today = LocalDate.now();
        
        HotItemByDate hotItem = hotItemRepository.findByProductAndOrderDate(product, today)
                .orElseThrow(() -> new BusinessException(
                        messageSource.getMessage("hot.item.notfound", 
                                new Object[]{product.getId(), today}, 
                                Locale.getDefault())));
        
        hotItem.decreaseCount(quantity);
        hotItemRepository.save(hotItem);
        log.info("Decreased purchase count for product: {} by {}", product.getId(), quantity);
    }

    /**
     * 특정 날짜의 인기 상품 조회
     */
    public Page<HotItemResponseDto> getTopItemsByDate(LocalDate date, Pageable pageable) {
        if (date.isAfter(LocalDate.now())) {
            throw new BusinessException(
                    messageSource.getMessage("hot.item.date.future", null, Locale.getDefault()));
        }
        return hotItemRepository.findTopItemsByDate(date, pageable)
                .map(HotItemResponseDto::from);
    }

    /**
     * 특정 기간의 인기 상품 조회
     */
    public Page<HotItemResponseDto> getTopItemsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        validateDateRange(startDate, endDate);
        return hotItemRepository.findTopItemsByDateRange(startDate, endDate, pageable)
                .map(HotItemResponseDto::from);
    }

    /**
     * 특정 상품의 특정 기간 판매량 합계 조회
     */
    public Integer getTotalPurchaseCount(Product product, LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return hotItemRepository.getTotalPurchaseCountByProductAndDateRange(product, startDate, endDate);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException(
                    messageSource.getMessage("hot.item.dateRange.invalid", null, Locale.getDefault()));
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > MAX_DATE_RANGE_DAYS) {
            throw new BusinessException(
                    messageSource.getMessage("hot.item.dateRange.tooLong", 
                            new Object[]{MAX_DATE_RANGE_DAYS}, 
                            Locale.getDefault()));
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new BusinessException(
                    messageSource.getMessage("hot.item.date.future", null, Locale.getDefault()));
        }
    }
} 