package com.horsepower.controller.product;

import com.horsepower.dto.product.ProductRequestDto;
import com.horsepower.dto.product.ProductResponseDto;
import com.horsepower.service.product.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이 클래스는 REST API 요청을 처리하는 컨트롤러임을 명시
@RequestMapping("/api/products") // 모든 메서드에 적용될 기본 URL 경로(prefix)
@RequiredArgsConstructor // final로 선언된 필드에 대해 생성자 자동 주입 (DI)
public class ProductController {

    private final ProductService productService; // 비즈니스 로직을 담당할 서비스 클래스 의존성 주입

    // ✅ 상품 등록 API (POST /api/products)
    // 요청 본문으로 받은 DTO를 기반으로 새 상품을 등록합니다.
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    // ✅ 상품 단일 조회 API (GET /api/products/{id})
    // 특정 상품 ID에 해당하는 상품을 조회합니다.
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // ✅ 상품 전체 조회 API (GET /api/products)
    // 등록된 모든 상품을 리스트 형태로 반환합니다.
    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ 상품 수정 API (PUT /api/products/{id})
    // 특정 ID의 상품 정보를 수정합니다.
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDto dto) {
        return productService.updateProduct(id, dto);
    }

    // ✅ 상품 삭제 API (DELETE /api/products/{id})
    // 특정 ID의 상품을 삭제합니다.
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}



