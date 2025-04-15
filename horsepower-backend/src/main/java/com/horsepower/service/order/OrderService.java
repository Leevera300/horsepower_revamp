package com.horsepower.service.order;

import com.horsepower.dto.order.OrderRequestDto;
import com.horsepower.dto.order.OrderResponseDto;
import com.horsepower.dto.address.AddressRequestDto;
import com.horsepower.entity.order.Order;
import com.horsepower.entity.order.Order.Status;
import com.horsepower.entity.product.Product;
import com.horsepower.entity.product_detail.ProductDetail;
import com.horsepower.entity.user.User;
import com.horsepower.exception.BusinessException;
import com.horsepower.repository.order.OrderRepository;
import com.horsepower.repository.product_detail.ProductDetailRepository;
import com.horsepower.repository.product.ProductRepository;
import com.horsepower.repository.user.UserRepository;
import com.horsepower.service.address.AddressService;
import com.horsepower.service.hot.HotItemService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final HotItemService hotItemService;
    private final MessageSource messageSource;

    // ✅ 주문 생성
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        log.info("Creating order for productDetailId: {}", dto.getProductDetailId());

        // 1. 상품 및 옵션 확인
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("order.product.notfound", null, Locale.getDefault())));
        ProductDetail detail = productDetailRepository.findById(dto.getProductDetailId())
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("order.product.detail.notfound", null, Locale.getDefault())));

        // 2. 재고 확인
        validateStock(detail, dto.getQuantity());

        // 3. 사용자 확인 (회원 or 비회원)
        User user = null;
        String email = null;

        if (dto.getUserId() != null) {
            // 회원 주문
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new BusinessException(messageSource.getMessage("order.user.notfound", null, Locale.getDefault())));
            email = user.getEmail(); // 회원의 이메일 사용
        } else if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            // 비회원 주문
            email = dto.getEmail();
        } else {
            throw new BusinessException(messageSource.getMessage("order.userOrEmail.required", null, Locale.getDefault()));
        }

        // 4. 총 가격 검증
        int calculatedTotalPrice = detail.getPrice() * dto.getQuantity();
        if (calculatedTotalPrice != dto.getTotalPrice()) {
            throw new BusinessException(messageSource.getMessage("order.price.mismatch", null, Locale.getDefault()));
        }

        // 5. 주문 생성
        Order order = new Order();
        String orderNumber = generateOrderNumber();
        order.setOrderNumber(orderNumber);
        order.setUser(user);
        order.setEmail(email);
        order.setProduct(product);
        order.setProductDetail(detail);
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setPaymentType(dto.getPaymentType());
        order.setOrderStatus(Status.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        // 6. 재고 감소
        detail.setQuantity(detail.getQuantity() - dto.getQuantity());
        productDetailRepository.save(detail);

        // 7. 주문 저장
        Order saved = orderRepository.save(order);
        log.info("Order saved with number: {}", saved.getOrderNumber());

        // ✅ 8. 주소 연동 저장
        for (AddressRequestDto addressDto : dto.getAddresses()) {
            // Verify the order exists before creating address
            if (!orderRepository.existsByOrderNumber(orderNumber)) {
                throw new BusinessException(messageSource.getMessage("order.notfound", null, Locale.getDefault()));
            }
            addressDto.setOrderNumber(orderNumber);
            addressDto.setUserId(user != null ? user.getId() : null);
            addressService.save(addressDto);
            log.info("Address saved for order: {}", orderNumber);
        }

        // ✅ 9. 인기 상품 기록 테이블 업데이트
        try {
            hotItemService.increasePurchaseCount(saved.getProduct(), saved.getQuantity());
            log.info("Hot item count updated for productId={}", saved.getProduct().getId());
        } catch (Exception e) {
            log.error("Failed to update hot items for product: {}", saved.getProduct().getId(), e);
            // Continue with order creation even if hot items update fails
        }

        // 10. 응답 반환
        return mapToResponseDto(saved);
    }

    // ✅ 주문 조회 (단건)
    public OrderResponseDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::mapToResponseDto)
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("order.notfound", null, Locale.getDefault())));
    }

    // ✅ 사용자별 주문 목록 조회
    public Page<OrderResponseDto> getOrdersByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUser_Id(userId, pageable)
                .map(this::mapToResponseDto);
    }

    // ✅ 이메일로 주문 목록 조회 (비회원)
    public Page<OrderResponseDto> getOrdersByEmail(String email, Pageable pageable) {
        return orderRepository.findByEmail(email, pageable)
                .map(this::mapToResponseDto);
    }

    // ✅ 주문 상태 업데이트
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, Status newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("order.notfound", null, Locale.getDefault())));

        // 주문 취소 시 재고 복구
        if (newStatus == Status.CANCELED && order.getOrderStatus() != Status.CANCELED) {
            ProductDetail detail = order.getProductDetail();
            detail.setQuantity(detail.getQuantity() + order.getQuantity());
            productDetailRepository.save(detail);
        }

        order.setOrderStatus(newStatus);
        Order updated = orderRepository.save(order);
        log.info("Order {} status updated to {}", orderId, newStatus);

        return mapToResponseDto(updated);
    }

    // ✅ 주문 취소
    @Transactional
    public OrderResponseDto cancelOrder(Long orderId) {
        return updateOrderStatus(orderId, Status.CANCELED);
    }

    // 주문 번호 생성 (랜덤 문자열 + 시간 기반)
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // 재고 검증
    private void validateStock(ProductDetail detail, int quantity) {
        if (detail.getQuantity() < quantity) {
            throw new BusinessException(messageSource.getMessage("order.stock.insufficient", 
                new Object[]{detail.getQuantity()}, Locale.getDefault()));
        }
    }

    // Entity to DTO 변환
    private OrderResponseDto mapToResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .email(order.getEmail())
                .productId(order.getProduct().getId())
                .productDetailId(order.getProductDetail().getId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .paymentType(order.getPaymentType())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}

