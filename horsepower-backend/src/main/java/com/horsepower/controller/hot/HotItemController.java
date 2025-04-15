package com.horsepower.controller.hot;

import com.horsepower.dto.hot.HotItemResponseDto;
import com.horsepower.service.hot.HotItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 인기 상품 (Hot Items) 관련 조회 API 컨트롤러
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/hot-items")
@RequiredArgsConstructor
public class HotItemController {

    private final HotItemService hotItemService;

    /**
     * 오늘의 인기 상품 조회
     */
    @GetMapping("/today")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<HotItemResponseDto>> getTodayHotItems(Pageable pageable) {
        LocalDate today = LocalDate.now();
        log.info("Fetching hot items for today: {} with pageable: {}", today, pageable);
        return ResponseEntity.ok(hotItemService.getTopItemsByDate(today, pageable));
    }

    /**
     * 날짜 범위로 인기 상품 조회
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<HotItemResponseDto>> getHotItemsByRange(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Pageable pageable
    ) {
        log.info("Fetching hot items from {} to {} with pageable: {}", start, end, pageable);
        
        if (end.isBefore(start)) {
            log.warn("Invalid date range: end date {} is before start date {}", end, start);
            throw new IllegalArgumentException("End date must be after start date");
        }
        
        return ResponseEntity.ok(hotItemService.getTopItemsByDateRange(start, end, pageable));
    }
}

