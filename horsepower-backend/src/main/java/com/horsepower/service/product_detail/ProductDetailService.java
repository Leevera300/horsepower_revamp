package com.horsepower.service.product_detail;

import com.horsepower.dto.product_detail.ProductDetailRequestDto;
import com.horsepower.dto.product_detail.ProductDetailResponseDto;
import com.horsepower.entity.product_detail.ProductDetail;
import com.horsepower.repository.product_detail.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service // 비즈니스 로직 담당
@RequiredArgsConstructor // final 필드 생성자 주입
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    // ✅ 상품 옵션 등록
    public ProductDetailResponseDto create(ProductDetailRequestDto dto) {
        ProductDetail detail = new ProductDetail();
        detail.setProductId(dto.getProductId());
        detail.setColor(dto.getColor());
        detail.setSize(dto.getSize());
        detail.setQuantity(dto.getQuantity());
        detail.setPrice(dto.getPrice());
        detail.setSale(dto.getSale());

        ProductDetail saved = productDetailRepository.save(detail);

        return toResponseDto(saved);
    }

    // ✅ 상품 옵션 단일 조회
    public ProductDetailResponseDto getById(Long id) {
        ProductDetail detail = productDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 옵션을 찾을 수 없습니다: " + id));
        return toResponseDto(detail);
    }

    // ✅ 상품 옵션 전체 조회
    public List<ProductDetailResponseDto> getAll() {
        return productDetailRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // ✅ 상품 옵션 수정
    public ProductDetailResponseDto update(Long id, ProductDetailRequestDto dto) {
        ProductDetail detail = productDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 옵션을 찾을 수 없습니다: " + id));

        detail.setColor(dto.getColor());
        detail.setSize(dto.getSize());
        detail.setQuantity(dto.getQuantity());
        detail.setPrice(dto.getPrice());
        detail.setSale(dto.getSale());
        detail.setUpdatedAt(LocalDateTime.now());

        ProductDetail updated = productDetailRepository.save(detail);
        return toResponseDto(updated);
    }

    // ✅ 상품 옵션 삭제
    public void delete(Long id) {
        if (!productDetailRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 상품 옵션을 찾을 수 없습니다: " + id);
        }
        productDetailRepository.deleteById(id);
    }

    // ✅ 응답 DTO 변환 메서드
    private ProductDetailResponseDto toResponseDto(ProductDetail detail) {
        return new ProductDetailResponseDto(
                detail.getId(),
                detail.getProductId(),
                detail.getColor(),
                detail.getSize(),
                detail.getQuantity(),
                detail.getPrice(),
                detail.getSale(),
                detail.getCreatedAt(),
                detail.getUpdatedAt()
        );
    }
}

