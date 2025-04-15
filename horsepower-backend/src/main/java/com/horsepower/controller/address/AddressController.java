package com.horsepower.controller.address;

import com.horsepower.dto.address.AddressRequestDto;
import com.horsepower.dto.address.AddressResponseDto;
import com.horsepower.entity.address.Address.Type;
import com.horsepower.service.address.AddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing address-related operations.
 * Handles creation and retrieval of delivery and billing addresses.
 */
@Slf4j
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Validated
public class AddressController {

    private final AddressService addressService;

    // ✅ 주소 등록 (배송지 or 청구지)
    /**
     * Creates a new address (delivery or billing) for an order.
     * @param dto The address data to create
     * @return The created address
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDto save(@RequestBody @Valid AddressRequestDto dto) {
        log.info("Creating address for order: {}", dto.getOrderNumber());
        return addressService.save(dto);
    }

    // ✅ 주문번호로 배송지/청구지 전체 조회
    /**
     * Retrieves all addresses (delivery and billing) for a specific order.
     * @param orderNumber The order number to find addresses for
     * @return List of addresses for the order
     */
    @GetMapping("/order/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponseDto> findByOrderNumber(
            @PathVariable @NotBlank(message = "{address.orderNumber.notblank}") String orderNumber
    ) {
        log.info("Finding addresses for order: {}", orderNumber);
        return addressService.findByOrderNumber(orderNumber);
    }

    // ✅ 주문번호 + 타입(DLV/BIL)으로 한 개 조회
    /**
     * Retrieves a specific address type (delivery or billing) for an order.
     * @param orderNumber The order number
     * @param type The address type (DELIVERY or BILLING)
     * @return The address
     */
    @GetMapping("/order/{orderNumber}/type")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseDto findByOrderNumberAndType(
            @PathVariable @NotBlank(message = "{address.orderNumber.notblank}") String orderNumber,
            @RequestParam @NotNull(message = "{address.type.notnull}") Type type
    ) {
        log.info("Finding {} address for order: {}", type, orderNumber);
        return addressService.findByOrderNumberAndType(orderNumber, type);
    }

    // ✅ 특정 유저 ID로 과거 주소 목록 조회
    /**
     * Retrieves all addresses associated with a user.
     * @param userId The user ID to find addresses for
     * @return List of addresses for the user
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponseDto> findByUserId(@PathVariable Long userId) {
        log.info("Finding addresses for user: {}", userId);
        return addressService.findByUserId(userId);
    }
}

