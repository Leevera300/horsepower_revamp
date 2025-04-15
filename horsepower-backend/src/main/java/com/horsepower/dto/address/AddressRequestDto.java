package com.horsepower.dto.address;

import com.horsepower.entity.address.Address.Type;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    private Long userId; // 비회원이면 null

    @NotBlank(message = "{address.orderNumber.notblank}")
    private String orderNumber;

    @NotNull(message = "{address.type.notnull}")
    private Type type; // DELIVERY or BILLING

    @NotBlank(message = "{address.countryRegion.notblank}")
    @Size(max = 48, message = "{address.countryRegion.size}")
    private String countryRegion;

    @NotBlank(message = "{address.firstName.notblank}")
    @Size(max = 20, message = "{address.firstName.size}")
    private String firstName;

    @NotBlank(message = "{address.lastName.notblank}")
    @Size(max = 20, message = "{address.lastName.size}")
    private String lastName;

    @NotBlank(message = "{address.address1.notblank}")
    @Size(max = 128, message = "{address.address1.size}")
    private String address1;

    @Size(max = 128, message = "{address.address2.size}")
    private String address2;

    @NotBlank(message = "{address.postalCode.notblank}")
    @Size(max = 12, message = "{address.postalCode.size}")
    private String postalCode;

    @NotBlank(message = "{address.city.notblank}")
    @Size(max = 48, message = "{address.city.size}")
    private String city;

    @Size(max = 48, message = "{address.state.size}")
    private String state;

    @NotBlank(message = "{address.phoneNumber.notblank}")
    @Size(max = 32, message = "{address.phoneNumber.size}")
    @Pattern(regexp = "^[0-9+\\-()\\s]+$", message = "{address.phoneNumber.pattern}")
    private String phoneNumber;
}

