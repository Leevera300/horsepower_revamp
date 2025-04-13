package com.horsepower.controller.order;

import com.horsepower.dto.order.OrderRequestDto;
import com.horsepower.dto.order.OrderResponseDto;
import com.horsepower.entity.order.Order.Status;
import com.horsepower.service.order.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    // ✅ 주문 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody @Valid OrderRequestDto dto) {
        return orderService.createOrder(dto);
    }

    // ✅ 주문 단건 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    // ✅ 회원 주문 목록 조회
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponseDto> getOrdersByUser(@PathVariable Long userId, Pageable pageable) {
        return orderService.getOrdersByUser(userId, pageable);
    }

    // ✅ 비회원 주문 목록 조회
    @GetMapping("/guest")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponseDto> getOrdersByEmail(
            @RequestParam @NotNull(message = "이메일은 필수입니다.") String email, 
            Pageable pageable) {
        return orderService.getOrdersByEmail(email, pageable);
    }

    // ✅ 주문 상태 변경
    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestParam @NotNull(message = "상태는 필수입니다.") Status status) {
        return orderService.updateOrderStatus(id, status);
    }

    // ✅ 주문 취소 (간편 라우팅)
    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}

