package com.horsepower.controller.product_pics;

import com.horsepower.dto.product_pics.ProductPicsRequestDto;
import com.horsepower.dto.product_pics.ProductPicsResponseDto;
import com.horsepower.service.product_pics.ProductPicsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-pics")
@RequiredArgsConstructor
public class ProductPicsController {

    private final ProductPicsService service;

    @PostMapping
    public ProductPicsResponseDto create(@RequestBody @Valid ProductPicsRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ProductPicsResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProductPicsResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/product/{productId}")
    public List<ProductPicsResponseDto> getByProductId(@PathVariable Long productId) {
        return service.getByProductId(productId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
