package com.horsepower.service.checkout;

import com.horsepower.dto.checkout.CheckoutRequestDto;
import com.horsepower.dto.checkout.CheckoutResponseDto;
import com.horsepower.entity.checkout.Checkout;
import com.horsepower.repository.checkout.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service // 비즈니스 로직을 처리하는 서비스 계층
@RequiredArgsConstructor // 생성자 자동 주입 (final 필드에 대해)
public class CheckoutService {

    private final CheckoutRepository repository;

    // ✅ 장바구니 항목 추가
    public CheckoutResponseDto create(CheckoutRequestDto dto) {
        Checkout c = new Checkout();
        c.setUserEmail(dto.getUserEmail());         // 유저 이메일(로그인 or 비회원도 가능)
        c.setProductId(dto.getProductId());         // 어떤 상품인지
        c.setProductDetailId(dto.getProductDetailId()); // 어떤 옵션인지
        c.setQuantity(dto.getQuantity());           // 수량

        Checkout saved = repository.save(c);        // DB에 저장
        return toDto(saved);                        // 저장된 항목 DTO로 반환
    }

    // ✅ 특정 유저의 장바구니 전체 조회
    public List<CheckoutResponseDto> getByUser(String userEmail) {
        return repository.findByUserEmail(userEmail)  // 해당 이메일로 전체 장바구니 항목 조회
                .stream()
                .map(this::toDto)                     // Entity → DTO 변환
                .collect(Collectors.toList());
    }

    // ✅ 장바구니 항목 수정 (수량 or 옵션 변경)
    public CheckoutResponseDto update(Long id, CheckoutRequestDto dto) {
        Checkout c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음: " + id));

        c.setProductId(dto.getProductId());
        c.setProductDetailId(dto.getProductDetailId());
        c.setQuantity(dto.getQuantity());
        c.setUpdatedAt(LocalDateTime.now());

        return toDto(repository.save(c)); // 수정 후 저장 → DTO 변환
    }

    // ✅ 장바구니 항목 삭제
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("해당 항목이 없습니다.");
        }
        repository.deleteById(id);
    }

    // ✅ Entity → DTO 변환 메서드
    private CheckoutResponseDto toDto(Checkout c) {
        return new CheckoutResponseDto(
                c.getId(),
                c.getUserEmail(),
                c.getProductId(),
                c.getProductDetailId(),
                c.getQuantity(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}

