package com.horsepower.controller.product;

import com.horsepower.dto.product.ProductRequestDto;
import com.horsepower.dto.product.ProductResponseDto;
import com.horsepower.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/api/products") // 모든 경로 앞에 붙는 prefix
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ✅ 상품 등록
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto dto) {
        return productService.createProduct(dto);
    }

    // ✅ 상품 단일 조회
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // ✅ 상품 전체 조회
    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ 상품 수정
    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody @Valid ProductRequestDto dto) {
        return productService.updateProduct(id, dto);
    }

    // ✅ 상품 삭제
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}




