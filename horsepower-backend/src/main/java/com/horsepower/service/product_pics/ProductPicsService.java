package com.horsepower.service.product_pics;

import com.horsepower.dto.product_pics.ProductPicsRequestDto;
import com.horsepower.dto.product_pics.ProductPicsResponseDto;
import com.horsepower.entity.product_pics.ProductPics;
import com.horsepower.repository.product_pics.ProductPicsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPicsService {

    private final ProductPicsRepository repository;

    public ProductPicsResponseDto create(ProductPicsRequestDto dto) {
        ProductPics pics = new ProductPics();
        pics.setProductId(dto.getProductId());
        pics.setImagePath(dto.getImagePath());

        ProductPics saved = repository.save(pics);

        return toDto(saved);
    }

    public ProductPicsResponseDto getById(Long id) {
        ProductPics pics = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보를 찾을 수 없습니다: " + id));
        return toDto(pics);
    }

    public List<ProductPicsResponseDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductPicsResponseDto> getByProductId(Long productId) {
        return repository.findByProductId(productId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("이미지를 찾을 수 없습니다: " + id);
        }
        repository.deleteById(id);
    }

    private ProductPicsResponseDto toDto(ProductPics p) {
        return new ProductPicsResponseDto(
                p.getId(),
                p.getProductId(),
                p.getImagePath(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}

