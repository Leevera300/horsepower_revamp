package com.horsepower.controller.product_detail;

import com.horsepower.dto.product_detail.ProductDetailRequestDto;
import com.horsepower.dto.product_detail.ProductDetailResponseDto;
import com.horsepower.service.product_detail.ProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 전용 컨트롤러임을 나타냄
@RequestMapping("/api/product-details") // 기본 경로 prefix
@RequiredArgsConstructor // 생성자 주입 (final 필드 자동 주입)
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    // ✅ 상품 옵션 등록
    @PostMapping
    public ProductDetailResponseDto create(@RequestBody @Valid ProductDetailRequestDto dto) {
        return productDetailService.create(dto);
    }

    // ✅ 상품 옵션 단일 조회
    @GetMapping("/{id}")
    public ProductDetailResponseDto getById(@PathVariable Long id) {
        return productDetailService.getById(id);
    }

    // ✅ 상품 옵션 전체 조회
    @GetMapping
    public List<ProductDetailResponseDto> getAll() {
        return productDetailService.getAll();
    }

    // ✅ 상품 옵션 수정
    @PutMapping("/{id}")
    public ProductDetailResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid ProductDetailRequestDto dto
    ) {
        return productDetailService.update(id, dto);
    }

    // ✅ 상품 옵션 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productDetailService.delete(id);
    }
}

