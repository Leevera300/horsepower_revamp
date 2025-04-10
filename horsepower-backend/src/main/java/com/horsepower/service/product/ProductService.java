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

@Service // 이 클래스는 비즈니스 로직을 담당하는 서비스 컴포넌트입니다.
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션 (final 필드 자동 주입)
public class ProductService {

    private final ProductRepository productRepository;

    // ✅ 상품 등록
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = new Product(); // 새 Product 객체 생성
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());

        Product saved = productRepository.save(product); // DB에 저장

        // 응답 DTO로 변환하여 반환
        return new ProductResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getCategory(),
                saved.getDescription(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    // ✅ 상품 전체 조회
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream() // 전체 상품 리스트 조회
                .map(p -> new ProductResponseDto(
                        p.getId(),
                        p.getName(),
                        p.getCategory(),
                        p.getDescription(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .collect(Collectors.toList()); // Product → ProductResponseDto 리스트로 변환
    }

    // ✅ 상품 단일 조회
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id) // ID로 상품 찾기
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    // ✅ 상품 수정
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setUpdatedAt(LocalDateTime.now()); // 수정 시간 갱신

        Product updated = productRepository.save(product); // 수정 후 저장

        return new ProductResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getCategory(),
                updated.getDescription(),
                updated.getCreatedAt(),
                updated.getUpdatedAt()
        );
    }

    // ✅ 상품 삭제
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + id);
        }

        productRepository.deleteById(id); // ID 기준 삭제
    }
}


