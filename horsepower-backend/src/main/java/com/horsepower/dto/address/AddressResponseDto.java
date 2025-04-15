package com.horsepower.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.horsepower.entity.address.Address.Type;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AddressResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("countryRegion")
    private String countryRegion;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("postalCode")
    private String postalCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}

