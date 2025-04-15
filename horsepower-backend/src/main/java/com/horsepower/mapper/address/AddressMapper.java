package com.horsepower.mapper.address;

import com.horsepower.dto.address.AddressRequestDto;
import com.horsepower.dto.address.AddressResponseDto;
import com.horsepower.entity.address.Address;
import com.horsepower.entity.user.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Address entity and DTOs.
 * Provides static methods for entity-DTO conversions.
 */
@Component
public class AddressMapper {

    private AddressMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts AddressRequestDto to Address entity.
     * @param dto The request DTO to convert
     * @param user The associated user (can be null for guest orders)
     * @return The converted Address entity
     */
    public static Address toEntity(AddressRequestDto dto, User user) {
        return Address.builder()
                .user(user)
                .orderNumber(dto.getOrderNumber())
                .type(dto.getType())
                .countryRegion(dto.getCountryRegion())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .postalCode(dto.getPostalCode())
                .city(dto.getCity())
                .state(dto.getState())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

    /**
     * Converts Address entity to AddressResponseDto.
     * @param address The entity to convert
     * @return The converted response DTO
     */
    public static AddressResponseDto toDto(Address address) {
        if (address == null) {
            return null;
        }

        return AddressResponseDto.builder()
                .id(address.getId())
                .userId(address.getUser() != null ? address.getUser().getId() : null)
                .orderNumber(address.getOrderNumber())
                .type(address.getType())
                .countryRegion(address.getCountryRegion())
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .state(address.getState())
                .phoneNumber(address.getPhoneNumber())
                .createdAt(address.getCreatedAt())
                .build();
    }

    /**
     * Updates an existing Address entity with data from AddressRequestDto.
     * @param address The entity to update
     * @param dto The request DTO containing new data
     * @param user The associated user (can be null for guest orders)
     */
    public static void updateEntity(Address address, AddressRequestDto dto, User user) {
        if (address == null || dto == null) {
            return;
        }

        address.setUser(user);
        address.setOrderNumber(dto.getOrderNumber());
        address.setType(dto.getType());
        address.setCountryRegion(dto.getCountryRegion());
        address.setFirstName(dto.getFirstName());
        address.setLastName(dto.getLastName());
        address.setAddress1(dto.getAddress1());
        address.setAddress2(dto.getAddress2());
        address.setPostalCode(dto.getPostalCode());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPhoneNumber(dto.getPhoneNumber());
    }
} 