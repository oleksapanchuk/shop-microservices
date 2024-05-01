package dev.oleksa.order.mapper;

import dev.oleksa.order.dto.AddressDto;
import dev.oleksa.order.entity.Address;

public class AddressMapper {

    public static AddressDto mapToAddressDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address mapToAddress(AddressDto addressDto) {

        return Address.builder()
                .id(addressDto.getId())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .country(addressDto.getCountry())
                .zipCode(addressDto.getZipCode())
                .build();
    }
}
