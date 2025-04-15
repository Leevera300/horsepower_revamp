package com.horsepower.service.address;

import com.horsepower.dto.address.AddressRequestDto;
import com.horsepower.dto.address.AddressResponseDto;
import com.horsepower.entity.address.Address;
import com.horsepower.entity.address.Address.Type;
import com.horsepower.entity.user.User;
import com.horsepower.exception.BusinessException;
import com.horsepower.mapper.address.AddressMapper;
import com.horsepower.repository.address.AddressRepository;
import com.horsepower.repository.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service for managing address-related operations.
 * Handles creation, retrieval, and management of delivery and billing addresses.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    // ✅ 주소 등록 (배송지 or 청구지)
    /**
     * Saves a new address (delivery or billing) for an order.
     * @param dto The address data to save
     * @return The saved address as a DTO
     */
    @Transactional
    public AddressResponseDto save(AddressRequestDto dto) {
        log.info("Saving address for order: {}", dto.getOrderNumber());

        User user = null;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new BusinessException(messageSource.getMessage("user.notfound", null, Locale.getDefault())));
        }

        Address address = AddressMapper.toEntity(dto, user);
        address.setCreatedAt(LocalDateTime.now());

        Address saved = addressRepository.save(address);
        log.info("Address saved with ID: {}", saved.getId());
        return AddressMapper.toDto(saved);
    }

    // ✅ 주문번호로 조회 (배송지 + 청구지)
    /**
     * Retrieves all addresses (delivery and billing) for a specific order.
     * @param orderNumber The order number to find addresses for
     * @return List of addresses for the order
     */
    @Transactional(readOnly = true)
    public List<AddressResponseDto> findByOrderNumber(String orderNumber) {
        log.info("Finding addresses for order: {}", orderNumber);
        return addressRepository.findByOrderNumber(orderNumber)
                .stream()
                .map(AddressMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 특정 주문의 배송지 or 청구지만 조회
    /**
     * Retrieves a specific address type (delivery or billing) for an order.
     * @param orderNumber The order number
     * @param type The address type (DELIVERY or BILLING)
     * @return The address as a DTO
     * @throws BusinessException if the address is not found
     */
    @Transactional(readOnly = true)
    public AddressResponseDto findByOrderNumberAndType(String orderNumber, Type type) {
        log.info("Finding {} address for order: {}", type, orderNumber);
        Address address = addressRepository.findByOrderNumberAndType(orderNumber, type)
                .orElseThrow(() -> new BusinessException(messageSource.getMessage("address.notfound", null, Locale.getDefault())));
        return AddressMapper.toDto(address);
    }

    // ✅ 유저 ID로 주소 목록 조회
    /**
     * Retrieves all addresses associated with a user.
     * @param userId The user ID to find addresses for
     * @return List of addresses for the user
     */
    @Transactional(readOnly = true)
    public List<AddressResponseDto> findByUserId(Long userId) {
        log.info("Finding addresses for user: {}", userId);
        return addressRepository.findByUserId(userId)
                .stream()
                .map(AddressMapper::toDto)
                .collect(Collectors.toList());
    }
}

