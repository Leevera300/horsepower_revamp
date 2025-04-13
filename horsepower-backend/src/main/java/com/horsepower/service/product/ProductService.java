package com.horsepower.service.product;

import com.horsepower.dto.product.ProductRequestDto;
import com.horsepower.dto.product.ProductResponseDto;
import com.horsepower.entity.product.Product;
import com.horsepower.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // ✅ 상품 등록
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setNameKr(dto.getNameKr());
        product.setNameEng(dto.getNameEng());
        product.setDescriptionKr(dto.getDescriptionKr());
        product.setDescriptionEng(dto.getDescriptionEng());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());

        Product saved = productRepository.save(product);

        return toDto(saved);
    }

    // ✅ 상품 전체 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 상품 단일 조회
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));

        return toDto(product);
    }

    // ✅ 상품 수정
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));

        product.setNameKr(dto.getNameKr());
        product.setNameEng(dto.getNameEng());
        product.setDescriptionKr(dto.getDescriptionKr());
        product.setDescriptionEng(dto.getDescriptionEng());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setUpdatedAt(LocalDateTime.now());

        Product updated = productRepository.save(product);

        return toDto(updated);
    }

    // ✅ 상품 삭제
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + id);
        }

        productRepository.deleteById(id);
    }

    // ✅ Entity → ResponseDto 변환 메서드
    private ProductResponseDto toDto(Product p) {
        return new ProductResponseDto(
                p.getId(),
                p.getNameKr(),
                p.getNameEng(),
                p.getDescriptionKr(),
                p.getDescriptionEng(),
                p.getCategory(),
                p.getSubCategory(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}



